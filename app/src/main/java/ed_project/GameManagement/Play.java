package ed_project.GameManagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.json.simple.parser.ParseException;

import ed_project.DataStructures.DoubleLinkedUnorderedList;
import ed_project.DataStructures.DoubleNode;
import ed_project.Enumerations.*;
import ed_project.Exceptions.*;
import ed_project.Game.*;
import ed_project.Interfaces.Game;
import ed_project.JSONFiles.*;

import java.lang.Math;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class Play implements Game{
    /**
     * Constante com o valor de experiencia que o jogador ganha por conquistar um portal
     */
    private final double EXPERIENCIA_CONQUISTAR = 40;

    /**
     * Constante com o valor de experiencia que o jogador ganha por fortalecer um portal
     */
    private final double EXPERIENCIA_FORTALECEU = 12;

    /**
     * Constante com o valor de experiencia que o jogador ganha por atacar um portal
     */
    private final double EXPERIENCIA_ATACAR = 7;

    /**
     * Constante com o valor de experiencia que o jogador ganha por recarregar num conector
     */
    private final double EXPERIENCIA_RECARREGAR = 3;

    /**
     * Constante com o valor de experiencia que o jogador ganha cada vez que interaja com algum local
     */
    private final double EXPERIENCIA_DEFAULT = 25;

    /**
     * Variavel que armazena o id do jogador controlado no momento
     */
    private int id_controlled_player;

    /**
     * Variavel que armazena o jogador que está a ser controlado para ser trabalhado na classe
     */
    private Player controlled_player;

    /**
     * Variavel que armazena o id do local atual do jogo
     */
    private int id_current_position;

    /**
     * Variavel que armazena o local atual do jogo para ser trabalhado na classe
     */
    private Local current_position;

    /**
     * Classe de gestao de jogadores para ter acesso a dados e manipula-los
     */
    private GestaoJogadores gj;

    /**
     * Classe de gestao de locais para ter acesso a dados e manipula-los
     */
    private GestaoLocais gl;

    /**
     * Classe de importação de ficheiros
     */
    private FileImport jsonfilesImp;

    /**
     * Classe de exportação de ficheiros
     */
    private FileExport jsonfilesExp;

    /**
     * Variavel para registar o tempo de chegada do player ao local atual
     */
    private LocalDateTime dateTime;

    /**
     * Lista de id de locais onde vao ser colocadas bombas
     */
    private DoubleLinkedUnorderedList<Integer> bombas;

    /**
     * Método construtor da classe Play, onde vai ter as outras classes de management instanciadas para assim ter acesso aos demais dados
     */
    public Play() {
        this.gj = new GestaoJogadores();
        this.gl = new GestaoLocais();
        this.jsonfilesImp = new FileImport();
        this.jsonfilesExp = new FileExport();
        this.bombas = new DoubleLinkedUnorderedList<>();
        this.dateTime = null;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getId_controlled_player() {
        return id_controlled_player;
    }

    public void setId_controlled_player(int id_controlled_player) {
        this.id_controlled_player = id_controlled_player;
    }

    public Player getControlled_player() {
        return controlled_player;
    }

    public void setControlled_player(Player controlled_player) {
        this.controlled_player = controlled_player;
    }

    public int getId_current_position() {
        return id_current_position;
    }

    public void setId_current_position(int id_current_position) {
        this.id_current_position = id_current_position;
    }

    public Local getCurrent_position() {
        return current_position;
    }

    public void setCurrent_position(Local current_position) {
        this.current_position = current_position;
    }

    public GestaoJogadores getGj() {
        return gj;
    }

    public void setGj(GestaoJogadores gj) {
        this.gj = gj;
    }

    public GestaoLocais getGl() {
        return gl;
    }

    public void setGl(GestaoLocais gl) {
        this.gl = gl;
    }

    public FileImport getJsonfilesImp() {
        return jsonfilesImp;
    }

    public void setJsonfilesImp(FileImport jsonfilesImp) {
        this.jsonfilesImp = jsonfilesImp;
    }

    /**
     * Método que é chamado quando o jogo começa para atribuir uma posição ao jogador caso este nao tenha uma posicao
     * atual e atribuido o valor ao jogador para ser mais facil o tratamento do objeto na classe
     * @param id id do jogador que vai ser controlado
     * @throws NoSuchElementException Caso não existe jogador com este id
     */
    public void startGame(int id) throws NoSuchElementException{
        this.id_controlled_player = id;
        this.controlled_player = searchIDPlayer(id);

        if (controlled_player.getLocalAtual() == null) {
            id_current_position = 0;
            current_position = searchIDLocal(id_current_position);
        } else {
            Player temp = searchIDPlayer(id_controlled_player);
            this.current_position = temp.getLocalAtual();
        }
        generateBombs();
        gl.printMatrix();
    }

    /**
     * Método da classe que é chamado no move() quando o jogador escolhe se mover. Sao apresentadas as opcoes que este tem para onde ir
     * @return string de possibilidades para onde o player pode ir
     * @throws NoSuchElementException caso nao exista nenhum player com o id
     */
    private String movimentacoesPossiveis() throws NoSuchElementException {
        String possibilidades = "\n";
        Local route_local = searchIDLocal(id_current_position);
        DoubleLinkedUnorderedList<Local> conexoes = route_local.getConexoes();
        DoubleNode<Local> current = conexoes.getHead();

        while (current != null) {
            if (current.getData() instanceof Portal) {
                possibilidades += "Portal \"" + ((Portal)current.getData()).getName() + "\" -> equipa " + ((Portal)current.getData()).getTeam()
                + " -> id " + ((Portal)current.getData()).getId() +"\n";
            } else {
                possibilidades += "Conector -> id " + ((Connector)current.getData()).getId() + "\n";
            }
            current = current.getNext();
        }

        return possibilidades;
    }

    /**
     * Método chamado sempre que o jogador escolhe se mover. No fim, é alterada a nova posicao do player para poder continuar o seu jogo
     */
    public void Move() throws EmptyCollectionException, NoSuchElementException, NumberFormatException, IOException {
        System.out.println(movimentacoesPossiveis());
        System.out.println("Escolha o id do seu destino.");
        int opcao;
        boolean found = false;
        boolean hasBomb = false;
        Local route_local = searchIDLocal(id_current_position);
        DoubleLinkedUnorderedList<Local> conexoes = route_local.getConexoes();
        DoubleNode<Local> current = conexoes.getHead();
        DoubleNode<Integer> current_localWBomb = bombas.getHead();

        do {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            opcao = Integer.parseInt(br.readLine());
            while(current != null && !found) {
                if (current.getData().getId() == opcao) {
                    found = true;
                } else {
                    current = current.getNext();
                }
            }
            current = conexoes.getHead();
        } while (!found);

        if (found == true) {
            gj.updatePlayerPosition(id_controlled_player, searchIDLocal(opcao));
            current_position = searchIDLocal(opcao);
            id_current_position = opcao;
            dateTime = LocalDateTime.now();

            while (current_localWBomb != null && !hasBomb) {
                if (current_localWBomb.getData() == id_current_position) {
                    gj.setNewPlayerEnergy(id_controlled_player, controlled_player.getCurrent_energy() * 0.50);
                    controlled_player = searchIDPlayer(id_controlled_player);
                    System.out.println("Oh não! Você encontrou uma bomba neste conector. Infelizmente ela explodiu e você perdeu 50% da sua energia na sequência. Resta-lhe agora " + controlled_player.getCurrent_energy() + " pontos de energia.");
                    bombas.remove(id_current_position);
                    hasBomb = true;
                } else {
                    current_localWBomb = current_localWBomb.getNext();
                }
            }
        }
    }

    /**
     * Método usado quando o jogador escolhe fortalecer um portal da sua equipa. Recebe uma "energy" que sera o valor dado ao portal
     */
    public void chargePortal(double energy) throws NoSuchElementException {
        double exp;

        if (energy > controlled_player.getCurrent_energy()) {
            System.out.println("Não tem energia suficiente");

        } else {
            if (current_position.getEnergy() == ((Portal) current_position).getEnergiaMaxima()) {
                System.out.println("O portal já tem a energia máxima permitida");

            } else {
                if (energy + current_position.getEnergy() <= ((Portal) current_position).getEnergiaMaxima()) {
                    exp = EXPERIENCIA_DEFAULT + EXPERIENCIA_FORTALECEU;
                    gl.addEnergyLocal(id_current_position, energy);
                    gj.setNewPlayerEnergy(id_controlled_player, controlled_player.getCurrent_energy() - energy);
                    updatePlayerExperience(id_controlled_player, controlled_player.getExperiencePoints() + EXPERIENCIA_DEFAULT + EXPERIENCIA_FORTALECEU);
                    updatePlayerLevel(id_controlled_player);
                    controlled_player = searchIDPlayer(id_controlled_player);
                    current_position = searchIDLocal(id_current_position);
                    System.out.println("Você fortaleceu o seu portal em " + energy + " pontos de energia. +" + exp + " pontos de experiência ganhos.");

                } else {
                    double energy_to_give;
                    exp = EXPERIENCIA_DEFAULT + EXPERIENCIA_FORTALECEU;
                    energy_to_give = ((Portal) current_position).getEnergiaMaxima() - current_position.getEnergy();
                    gl.setNewPortalEnergy(id_current_position, current_position.getEnergy() + energy_to_give);
                    gj.setNewPlayerEnergy(id_controlled_player, controlled_player.getCurrent_energy() - energy + (energy - energy_to_give));
                    updatePlayerExperience(id_controlled_player, controlled_player.getExperiencePoints() + EXPERIENCIA_DEFAULT + EXPERIENCIA_FORTALECEU);
                    updatePlayerLevel(id_controlled_player);
                    controlled_player = searchIDPlayer(id_controlled_player);
                    current_position = searchIDLocal(id_current_position);
                    System.out.println("Você fortaleceu o seu portal em " + energy_to_give + " pontos de energia. +" + exp + " pontos de experiência ganhos.");

                }
            }
        }
    }

    /**
     * Método usado quando o jogador escolhe atacar um portal que esteja neutro ou na posse da equipa adversária.
     */
    public void attackPortal() throws NoSuchElementException, EmptyCollectionException, NoSuchTeamExist, NotAcceptedValue {
        double energiaSobra, exp;

        if (((Portal) current_position).getTeam().equals(Teams.Neutro)) {
            if (controlled_player.getCurrent_energy() >= ((Portal) current_position).getEnergiaMaxima() * 0.25) {

                energiaSobra = controlled_player.getCurrent_energy()- (((Portal) current_position).getEnergiaMaxima() * 0.25);
                exp = EXPERIENCIA_DEFAULT + EXPERIENCIA_CONQUISTAR;
                gj.setNewPlayerEnergy(id_controlled_player, energiaSobra);
                gl.setNewPortalEnergy(id_current_position, 0);
                incrementNPortais(id_controlled_player);
                switchLocalTeams(id_current_position, controlled_player.getTeam());
                newOwnership(controlled_player);
                updatePlayerExperience(id_controlled_player, controlled_player.getExperiencePoints() + EXPERIENCIA_DEFAULT + EXPERIENCIA_CONQUISTAR);
                updatePlayerLevel(id_controlled_player);
                controlled_player = searchIDPlayer(id_controlled_player);
                current_position = searchIDLocal(id_current_position);
                System.out.println("Você conquistou o portal. +" + exp + " pontos de experiência ganhos.");

            } else {

                energiaSobra = (((Portal) current_position).getEnergiaMaxima() * 0.25) - controlled_player.getCurrent_energy();
                exp = EXPERIENCIA_DEFAULT + EXPERIENCIA_ATACAR;
                gl.setNewPortalEnergy(id_controlled_player, energiaSobra);
                gj.setNewPlayerEnergy(id_controlled_player, 0);
                updatePlayerExperience(id_controlled_player, controlled_player.getExperiencePoints() + EXPERIENCIA_DEFAULT + EXPERIENCIA_ATACAR);
                updatePlayerLevel(id_controlled_player);
                controlled_player = searchIDPlayer(id_controlled_player);
                System.out.println("Não tem energia suficiente para conquistar este portal e por isso permanece neutro. +" + exp + " pontos de experiência ganhos.");

            }

        } else {

            if (controlled_player.getCurrent_energy() >= current_position.getEnergy()) {
                int IDAntigoOwnership = findOwnership();

                energiaSobra = controlled_player.getCurrent_energy() - current_position.getEnergy();

                gj.setNewPlayerEnergy(id_controlled_player, energiaSobra);
                gl.setNewPortalEnergy(id_current_position, 0);
                incrementNPortais(id_controlled_player);
                decrementNPortais(IDAntigoOwnership);
                newOwnership(controlled_player);

                if (controlled_player.getTeam().equals(Teams.Giants)) {
                    switchLocalTeams(id_current_position, Teams.Giants);

                } else if (controlled_player.getTeam().equals(Teams.Sparks)) {
                    switchLocalTeams(id_current_position, Teams.Sparks);
                }
                updatePlayerExperience(id_controlled_player, controlled_player.getExperiencePoints() + EXPERIENCIA_DEFAULT + EXPERIENCIA_CONQUISTAR);
                updatePlayerLevel(id_controlled_player);
                controlled_player = searchIDPlayer(id_controlled_player);
                current_position = searchIDLocal(id_current_position);
                exp = EXPERIENCIA_DEFAULT + EXPERIENCIA_CONQUISTAR;
                System.out.println("Você conquistou o portal. +" + exp + " pontos de experiência ganhos.");

            } else if (controlled_player.getCurrent_energy() < current_position.getEnergy()) {

                energiaSobra = current_position.getEnergy() - controlled_player.getCurrent_energy();
                gl.setNewPortalEnergy(id_current_position, energiaSobra);
                gj.setNewPlayerEnergy(id_controlled_player, 0);
                updatePlayerExperience(id_controlled_player, controlled_player.getExperiencePoints() + EXPERIENCIA_DEFAULT + EXPERIENCIA_ATACAR);
                updatePlayerLevel(id_controlled_player);
                controlled_player = searchIDPlayer(id_controlled_player);
                exp = EXPERIENCIA_DEFAULT + EXPERIENCIA_ATACAR;
                System.out.println("Não tem energia suficiente para conquistar este portal e por isso permanece na posse da equipa adversária. +" + exp + " pontos de experiência ganhos.");

            }
        }
    }

    /**
     * Método usado quando o jogador escolhe recarregar a enegia quando entra em contacto com um conector.
     */
    public void refillEnergy() throws NoSuchElementException {
        Connector connector = (Connector) searchIDLocal(id_current_position);
        DoubleNode<Interacao> current = connector.getInteracoes().getHead();
        boolean found = false;
        double other_player_in_this_local = checkPlayerInCurPos();

        if (other_player_in_this_local == -1 || controlled_player.getCurrent_energy() > other_player_in_this_local) {

            while (current != null && !found) {
                if (current.getData().getPlayer().getId() == id_controlled_player) {
                    found = true;
                } else {
                    current = current.getNext();
                }
            }
            
            double exp;
            
            if (found = true) {
                if (current == null) {
                    double energy_temp = controlled_player.getCurrent_energy() + connector.getEnergy();
                    gj.setNewPlayerEnergy(id_controlled_player, controlled_player.getCurrent_energy() + connector.getEnergy());
                    updatePlayerExperience(id_controlled_player, controlled_player.getExperiencePoints() + EXPERIENCIA_DEFAULT + EXPERIENCIA_RECARREGAR);
                    updatePlayerLevel(id_controlled_player);
                    controlled_player = searchIDPlayer(id_controlled_player);
                    current_position = searchIDLocal(id_current_position);
                    Interacao interacao = new Interacao(controlled_player, interactionType.RECARREGOU, LocalDateTime.now());
                    addInteraction(interacao);
                    exp = EXPERIENCIA_DEFAULT + EXPERIENCIA_RECARREGAR;
                    System.out.println("Você recarregou a sua energia e tem agora " + energy_temp + " pontos de energia. +"+ exp + " pontos de experiência ganhos.");

                } else {
                    long minutos = current.getData().getTime().until(LocalDateTime.now(), ChronoUnit.MINUTES);
                    if (minutos > connector.getCooldown()) {
                        double energy_temp = controlled_player.getCurrent_energy() + connector.getEnergy();
                        gj.setNewPlayerEnergy(id_controlled_player, controlled_player.getCurrent_energy() + connector.getEnergy());
                        updatePlayerExperience(id_controlled_player, controlled_player.getExperiencePoints() + EXPERIENCIA_DEFAULT + EXPERIENCIA_RECARREGAR);
                        updatePlayerLevel(id_controlled_player);
                        controlled_player = searchIDPlayer(id_controlled_player);
                        current_position = searchIDLocal(id_current_position);
                        Interacao interacao = new Interacao(controlled_player, interactionType.RECARREGOU, LocalDateTime.now());
                        removeInteraction();
                        addInteraction(interacao);
                        exp = EXPERIENCIA_DEFAULT + EXPERIENCIA_RECARREGAR;
                        System.out.println("Você recarregou a sua energia e tem agora " + energy_temp + " pontos de energia. +"+ exp + " pontos de experiência ganhos.");
        
                    } else {
                        System.out.println("Já foi feito um carregamento de energia com este conector à menos de " + connector.getCooldown() + " minutos");
                    }
                }
            }
        } else {
            System.out.println("Não pode carregar energia uma vez que outro jogador com mais energia já se encontra neste conector.");
        }
        
    }

    /**
     * Método da classe que gera um numero de bombas em portais aleatorios baseado no numero destes que existe.
     * @throws NoSuchElementException
     */
    private void generateBombs() throws NoSuchElementException{
        int numberOfLocals = gl.getLocais().getCount();
        int numberOfConectors = 0;
        int numberOfBombs = 0;
        int numberOfBombsGenerated = 0;
        int localIdWithBomb = 0;

        DoubleNode<Local> current = gl.getLocais().getHead();
        
        while (current != null) {
            if (current.getData() instanceof Connector) {
                numberOfConectors++;
                current = current.getNext();
            } else {
                current = current.getNext();
            }
        }

        numberOfBombs = (int) (numberOfConectors * 0.25);

        while (numberOfBombs != numberOfBombsGenerated) {
            localIdWithBomb = (int) (Math.random() * numberOfLocals);
            if (searchIDLocal(localIdWithBomb) instanceof Connector) {
                bombas.addToRear(localIdWithBomb);
                numberOfBombsGenerated++;
            }
        }
    }

    private void newOwnership(Player ownership) throws NoSuchElementException {
        gl.setNewOwnership(id_current_position, ownership);
        current_position = searchIDLocal(id_current_position);
    }

    public void addPlayer(String name, int team) {
        try {
            gj.addPlayer(name, team);
        } catch (NoSuchTeamExist e) {
            e.printStackTrace();
        }
    }

    public void removePlayer(int id) {
        try {
            gj.removePlayer(id);
        } catch (NoSuchElementException | EmptyCollectionException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayer(int id, String name) {
        try {
            gj.updatePlayer(id, name);
        } catch (NoSuchElementException | EmptyCollectionException e) {
            e.printStackTrace();
        }
    }

    public void switchPlayerTeams(int id) {
        try {
            gj.switchTeams(id);
        } catch (NoSuchElementException | EmptyCollectionException | NoSuchTeamExist e) {
            e.printStackTrace();
        }
    }

    public void updatePlayerLevel(int id) {
        try {
            gj.updatePlayerLevel(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayerExperience(int id, double expPoints) {
        try {
            gj.updatePlayerExperience(id, expPoints);
        } catch (NoSuchElementException | NotAcceptedValue e) {
            e.printStackTrace();
        }
    }

    public void switchLocalTeams(int id, Teams team) {
        try {
            gl.switchTeams(id, team);
            current_position = searchIDLocal(id_current_position);
        } catch (NoSuchElementException | EmptyCollectionException | NoSuchTeamExist e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para adicionar um portal
     * @param lat latitudo do portal
     * @param lon longitude do portal
     * @param name nome do portal
     * @param maxEnergy energia maxima do portal
     * @param team equipa do portal
     */
    public void addPortal(double lat, double lon, String name, double maxEnergy, Teams team) {
        gl.addPortal(lat, lon, name, maxEnergy, team);
    }

    /**
     * Metodo para adicionar conector
     * @param lat latitude do conector
     * @param lon longitude do conector
     * @param energy 
     * @param cooldown
     */
    public void addConnector(double lat, double lon, double energy, int cooldown) {
        gl.addConnector(lat, lon, energy, cooldown);
    }

    /**
     * Metodo que adiciona rotas num só sentido
     * @param id1
     * @param id2
     * @throws InvalidRoute
     * @throws NoSuchElementException
     */
    public void addRotaUmSentido(int id1, int id2) throws InvalidRoute, NoSuchElementException{
        Local local1 = searchIDLocal(id1);
        Local local2 = searchIDLocal(id2);
        
        gl.adicionarRotasUmSentido(local1, local2);
    }

    public void addRotaDoisSentidos(int id1, int id2) throws NoSuchElementException, InvalidRoute{
        Local local1 = searchIDLocal(id1);
        Local local2 = searchIDLocal(id2);

        gl.adicionarRotasDoisSentidos(local1, local2);
    }

    public void removeRotaUmSentido(int id1, int id2) throws InvalidRoute, NoSuchElementException, EmptyCollectionException{
        Local local1 = searchIDLocal(id1);
        Local local2 = searchIDLocal(id2);

        gl.removerRotasUmSentido(local1, local2);
    }

    public void removeRotaDoisSentidos(int id1, int id2) throws NoSuchElementException, InvalidRoute, EmptyCollectionException{
        Local local1 = searchIDLocal(id1);
        Local local2 = searchIDLocal(id2);
        
        gl.removerRotasDoisSentidos(local1, local2);
    }

    public void listarPlayersPorTeam(){
        gj.listarPlayersPorTeam();
    }

    public void listarPlayerPorNivel() throws EmptyCollectionException{
        gj.listarPlayerPorNivel();
    }

    public void listarPlayerPorPortaisConquistados() throws EmptyCollectionException{
        gj.listarPlayerPorPortaisConquistados();
    }

    public void listarPortaisPorEnergia() throws EmptyCollectionException{
        gl.listarPortaisPorEnergia();
    }

    public void listarPortaisPorOwnership(){
        gl.listarPortaisPorOwnership(gj);
    }

    public void listarDadosGeraisPortais(){
        gl.listarDadosGeraisPortais();
    }

    public void listarConnectorMenorCooldown() throws EmptyCollectionException{
        gl.listarConnectorMenorCooldown();
    }

    public void listarDadosGeraisConectores() {
        gl.listarDadosGeraisConectores();
    }

    public String caminhoMaisCurto(int id1, int id2) throws NoSuchElementException, EmptyCollectionException {
        return gl.caminhoMaisCurto(id1, id2);
    }

    public String camMaisCurtoPor1Vertice(int id1, int id2, int id3) throws NoSuchElementException, EmptyCollectionException {
        return gl.camMaisCurtoPor1Vertice(id1, id2, id3);
    }

    public void addInteraction(Interacao interacao) {
        try {
            gl.addInteracoes(id_current_position, interacao);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    public void removeInteraction() {
        try {
            gl.removeInteracoes(id_controlled_player, id_current_position);
        } catch (NoSuchElementException | EmptyCollectionException e) {
            e.printStackTrace();
        }
    }

    public void exportToJSONGlobal() throws IOException {
        jsonfilesExp.exportToJSONGlobal(gl, gj);
    }

    public void exportToJSONRoutes() throws IOException {
        jsonfilesExp.exportToJSONRoutes(gl);
    }

    public void exportToJSONPlayers() throws IOException {
        jsonfilesExp.exportToJSONPlayers(gj);
    }

    public void exportToJSONLocals() throws IOException {
        jsonfilesExp.exportToJSONLocals(gl);
    }

    public void importFromJSON(String filename) throws FileNotFoundException, IOException, ParseException, InvalidRoute, NotAcceptedValue, java.text.ParseException {
        jsonfilesImp.importFromJSON(filename, gj, gl);
    }

    public void importPlayersFromJSON(String filename) throws FileNotFoundException, IOException, ParseException, NotAcceptedValue{
        jsonfilesImp.importPlayersFromJSON(filename, gj);
    }

    public void importLocalsFromJSON(String filename) throws FileNotFoundException, IOException, ParseException, NotAcceptedValue{
        jsonfilesImp.importLocalsFromJSON(filename, gj, gl);
    }

    public void importRoutesFromJSON(String filename) throws FileNotFoundException, IOException, ParseException, InvalidRoute {
        jsonfilesImp.importRoutesFromJSON(filename, gl);
    }

    public void incrementNPortais(int id) throws NoSuchElementException{
        gj.incrementNPortais(id);
    }

    public void decrementNPortais(int id) throws NoSuchElementException {
        gj.decrementNPortais(id);
    }

    public void removeLocal(int id) {
        try {
            gl.removeLocal(id);
        } catch (EmptyCollectionException | NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    public void updateLocalCoordinates(int id, double lat, double lon) {
        try {
            gl.updateLocalCoordinates(id, lat, lon);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    public void updatePortalName(int id, String name) {
        try {
            gl.updatePortalName(id, name);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo usado para obter o jogador do id fornecido
     * @param id id do jogador
     * @return jogador do id ou excessao caso nao exista nenhum jogador com o id
     * @throws NoSuchElementException caso nao exista nenhum jogador com o id
     */
    private Player searchIDPlayer(int id) throws NoSuchElementException {
        DoubleNode<Player> current = gj.getPlayers().getHead();
        boolean found = false;

        while (current != null && !found) {
            if (id == current.getData().getId()) {
                found = true;
            } else {
                current = current.getNext();
            }
        }

        if (found == false) {
            throw new NoSuchElementException("Não existe nenhum jogador com o id indicado");
        } else {
            return current.getData();
        }
    }

    /**
     * Método usado para obter o local do id fornecido
     * @param id id do local
     * @return local do id ou excessao caso nao exista nenhum local com o id
     * @throws NoSuchElementException caso nao exista nenhum local com o id
     */
    private Local searchIDLocal(int id) throws NoSuchElementException {
        DoubleNode<Local> current = gl.getLocais().getHead();
        boolean found = false;

        while (current != null && !found) {
            if (id == current.getData().getId()) {
                found = true;
            } else {
                current = current.getNext();
            }
        }

        if (found == false) {
            throw new NoSuchElementException("Não existe nenhum local com o id indicado");
        } else {
            return current.getData();
        }
    }

    /**
     * Método para listar os jogadores possíveis para jogar
     */
    public void listarJogadores(){
        System.out.println(gj.listarJogadores());
    }

    /**
     * Informações sobre a atual posição do jogador
     * @return uma string com a informaçao
     */
    public String status() {
        String str = "";

        if (current_position instanceof Portal) {
            str = "Você encontra-se no portal " + ((Portal)current_position).getName() + ", de id " 
            + ((Portal)current_position).getId() + " que pertence à equipa " + ((Portal)current_position).getTeam();
        } else {
            str = "Você encontra-se no portal de id " + ((Portal)current_position).getId();
        }

        return str;
    }

    /**
     * confirma se o ID do local existe
     * @param id id do local
     * @return true se existir, false se não
     */
    public boolean confirmExistLocalID(int id){
        DoubleNode<Local> current = gl.getLocais().getHead();
        boolean found = false;

        while (current != null && !found) {
            if (id == current.getData().getId()) {
                found = true;
            } else {
                current = current.getNext();
            }
        }
        return found;
    }

    public int locaisSize() {
        return gl.getLocais().getCount();
    }

    /**
     * Método usado para conferir se ja existe um jogador no local atual
     * @return a energia do jogador caso ele la esteja ou -1 caso nao esteja nenhum jogador no local
     */
    private double checkPlayerInCurPos(){
        DoubleNode<Player> current = gj.getPlayers().getHead();
        boolean found = false;

        while (current != null && !found) {
            if (current.getData().getLocalAtual() == controlled_player.getLocalAtual()) {
                if (current.getData() == controlled_player) {
                    current = current.getNext();
                } else {
                    found = true;
                }
            } else {
                current = current.getNext();
            }
        }
        if (found == true) {
            return current.getData().getCurrent_energy();
        } else {
            return -1;
        }
    }

    /**
     * Metodo para detetar o id do dono do portal
     * @return id do dono ou -1 caso nao tenha dono
     */
    private int findOwnership() {
        DoubleNode<Player> current = gj.getPlayers().getHead();
        boolean found = false;

        while (current != null && !found) {
            if (((Portal)current_position).getOwnership() == current.getData()) {
                found = true;
            } else {
                current = current.getNext();
            }
        }

        if (found == true) {
            return ((Portal)current_position).getOwnership().getId();
        } else {
            return -1;
        }
    }

    /**
     * Menu usado para o utilizador jogar, mais concretamente, escolher as ações permitidas dentro do jogo
     * @throws NumberFormatException excessao formato de numero
     * @throws IOException excessao de input/output
     * @throws EmptyCollectionException excessao caso a lista esteja vazia
     * @throws NoSuchElementException excessao caso o elemento do id nao seja encontrado
     * @throws NoSuchTeamExist excessao caso a equipa nao seja encontrada
     * @throws NotAcceptedValue excessao caso seja um valor não aceite
     */
    public void gamingMenu() throws NumberFormatException, IOException, EmptyCollectionException, NoSuchElementException, NoSuchTeamExist, NotAcceptedValue {
        if (current_position instanceof Portal) {
            int opcao2, opcao3, opcao4;
            if (((Portal)current_position).getTeam().equals(Teams.Neutro)) {
                System.out.println("Você encontra-se num portal Neutro. O que pretende fazer?");
                System.out.println("0. Sair do jogo");
                System.out.println("1. Mover-se");
                System.out.println("2. Atacar o portal");
                do {
                    BufferedReader br3 = new BufferedReader(new InputStreamReader(System.in));
                    opcao2 = Integer.parseInt(br3.readLine());
                    if (opcao2 < 0 || opcao2 > 2) {
                        System.out.println("\n     --- Opcao invalida ---\n");
                    }
                } while (opcao2 < 0 && opcao2 > 2);
                switch(opcao2) {
                    case 1:
                        Move();
                        gamingMenu();
                        break;
                    case 2:
                        if (controlled_player.getCurrent_energy() == 0) {
                            System.out.println("Você não tem energia disponível para atacar um portal. Diriga-se a um conector para recarregar.");
                        } else {
                            attackPortal();
                        }
                        gamingMenu();
                        break;
                }
            } else if ((((Portal)current_position).getTeam().equals(Teams.Giants) && controlled_player.getTeam().equals(Teams.Giants)) 
            || (((Portal)current_position).getTeam().equals(Teams.Sparks) && controlled_player.getTeam().equals(Teams.Sparks))) {
                System.out.println("Você encontra-se num portal que pertence à sua equipa. O que pretende fazer?");
                System.out.println("0. Sair do jogo");
                System.out.println("1. Mover-se");
                System.out.println("2. Fortalecer o portal");
                do {
                    BufferedReader br4 = new BufferedReader(new InputStreamReader(System.in));
                    opcao3 = Integer.parseInt(br4.readLine());
                    if (opcao3 < 0 || opcao3 > 2) {
                        System.out.println("\n     --- Opcao invalida ---\n");
                    }
                } while (opcao3 < 0 && opcao3 > 2);
                switch(opcao3) {
                    case 1:
                        Move();
                        gamingMenu();
                        break;
                    case 2:
                        double energia;
                        if (controlled_player.getCurrent_energy() == 0) {
                            System.out.println("Você não tem energia disponível para atacar um portal. Diriga-se a um conector para recarregar.");
                        } else {
                            do {
                                System.out.println("Qual é o valor da energia que quer fornecer?");
                                BufferedReader br5 = new BufferedReader(new InputStreamReader(System.in));
                                energia = Double.parseDouble(br5.readLine());
                                if (energia < 0) {
                                    System.out.println("\n     --- Nao é possivel fornecer energia negativa ---\n");
                                } else if (energia > controlled_player.getCurrent_energy()) {
                                    System.out.println("\n     --- Nao é possivel fornecer energia superior à atual ---\n");
                                }
                            } while (energia < 0 && energia > controlled_player.getCurrent_energy());
                            chargePortal(energia);
                        }
                        gamingMenu();
                        break;
                }
            } else if ((((Portal)current_position).getTeam().equals(Teams.Giants) && controlled_player.getTeam().equals(Teams.Sparks)) 
            || (((Portal)current_position).getTeam().equals(Teams.Sparks) && controlled_player.getTeam().equals(Teams.Giants))) {
                System.out.println("Você encontra-se num portal que pertence à equipa adversaria. O que pretende fazer?");
                System.out.println("0. Sair do jogo");
                System.out.println("1. Mover-se");
                System.out.println("2. Atacar o portal");
                do {
                    BufferedReader br4 = new BufferedReader(new InputStreamReader(System.in));
                    opcao4 = Integer.parseInt(br4.readLine());
                    if (opcao4 < 0 || opcao4 > 2) {
                        System.out.println("\n     --- Opcao invalida ---\n");
                    }
                } while (opcao4 < 0 && opcao4 > 2);
                switch(opcao4) {
                    case 1:
                        Move();
                        gamingMenu();
                        break;
                    case 2:
                        if (controlled_player.getCurrent_energy() == 0) {
                            System.out.println("Você não tem energia disponível para atacar um portal. Diriga-se a um conector para recarregar.");
                        } else {
                            attackPortal();
                        }
                        gamingMenu();
                        break;
                }
            }
        } else if (current_position instanceof Connector) {
            int opcao1;
            System.out.println("Você encontra-se num conector. O que pretende fazer?");
            System.out.println("0. Sair do jogo");
            System.out.println("1. Mover-se");
            System.out.println("2. Recarregar energia");
            do {
                BufferedReader br6 = new BufferedReader(new InputStreamReader(System.in));
                opcao1 = Integer.parseInt(br6.readLine());
                if (opcao1 < 0 || opcao1 > 2) {
                    System.out.println("\n     --- Opcao invalida ---\n");
                }
            } while (opcao1 < 0 && opcao1 > 2);
            switch(opcao1) {
                case 1:
                    Move();
                    gamingMenu();
                    break;
                case 2:
                    refillEnergy();
                    gamingMenu();
                    break;
            }
        }
    }
    
}