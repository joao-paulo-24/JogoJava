package ed_project.GameManagement;

import ed_project.DataStructures.DoubleLinkedUnorderedList;
import ed_project.DataStructures.DoubleNode;
import ed_project.Enumerations.Teams;
import ed_project.Exceptions.*;
import ed_project.Game.*;
import ed_project.Interfaces.PlayerManagement;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class GestaoJogadores implements PlayerManagement {
    /**
     * Lista de players
     */
    private DoubleLinkedUnorderedList<Player> players;

    /**
     * Metodo construtor para a classe Gestao de jogadores
     */
    public GestaoJogadores() {
        this.players = new DoubleLinkedUnorderedList<Player>();
    }

    public DoubleLinkedUnorderedList getPlayers() {
        return players;
    }

    public void setPlayers(DoubleLinkedUnorderedList players) {
        this.players = players;
    }

    /**
     * Metodo que permite adicionar jogadores à lista de jogadores
     * @param name nome do novo jogador
     * @param team equipa do novo jogador
     * @throws NoSuchTeamExist caso a equipa passada nao exista
     */
    public void addPlayer(String name, int team) throws NoSuchTeamExist {
        switch (team) {
            case 1:
                Player player_temp = new Player(name, Teams.Sparks);
                players.addToRear(player_temp);
                break;
            case 2:
                Player player_temp2 = new Player(name, Teams.Giants);
                players.addToRear(player_temp2);
                break;
            default:
                throw new NoSuchTeamExist("O nome de equipa indicado não é válido");
        }
    }

    /**
     * Metodo usado para remover um jogador da lista de jogadores
     * @param id id do jogador a ser removido
     * @throws NoSuchElementException caso o id nao exista
     * @throws EmptyCollectionException caso a lista esteja vazia
     */
    public void removePlayer(int id) throws NoSuchElementException, EmptyCollectionException {
        DoubleNode<Player> current = new DoubleNode<>();

        current = searchID(id);

        this.players.remove(current.getData());
    }

    /**
     * Metodo usado para atualizar o jogador
     * @param id id do jogador a atualizar
     * @param name novo nome do jogador
     * @throws NoSuchElementException caso o id nao exista
     * @throws EmptyCollectionException caso a lista esteja vazia
     */
    public void updatePlayer(int id, String name) throws NoSuchElementException, EmptyCollectionException {
        DoubleNode<Player> current = new DoubleNode<>();

        current = searchID(id);

        current.getData().setName(name);
    }

    /**
     * Metodo usado para incrementar o numero de portais quando o jogador conquista algum
     * @param id id do jogador a atualizar o numero de portais
     * @throws NoSuchElementException caso o id nao exista
     */
    public void incrementNPortais(int id) throws NoSuchElementException {
        DoubleNode<Player> current = new DoubleNode<>();
        int newNPortais;
        
        current = searchID(id);
        newNPortais = current.getData().getnPortais() + 1;
        current.getData().setnPortais(newNPortais);
    }

    /**
     * Metodo usado para decrementar o numero de portais quando o jogador perde algum
     * @param id id do jogador a atualizar o numero de portais
     * @throws NoSuchElementException caso o id nao exista
     */
    public void decrementNPortais(int id) throws NoSuchElementException {
        DoubleNode<Player> current = new DoubleNode<>();
        int newNPortais;
        
        current = searchID(id);
        newNPortais = current.getData().getnPortais() - 1;
        current.getData().setnPortais(newNPortais);
    }

    /**
     * Metodo usado para atualizar a posicao atual de um jogador no jogo
     * @param id id do jogador a atualizar
     * @param local local fornecido que será o novo local atual
     * @throws NoSuchElementException caso o id nao exista
     */
    public void updatePlayerPosition(int id, Local local) throws NoSuchElementException {
        DoubleNode<Player> current = new DoubleNode<>();

        current = searchID(id);

        current.getData().setLocalAtual(local);
    }

    /**
     * Metodo usado para atualizar o nivel do jogador
     * @param id id do jogador a atualizar
     * @throws NoSuchElementException caso o id nao exista
     */
    public void updatePlayerLevel(int id) throws NoSuchElementException {
        DoubleNode<Player> current = new DoubleNode<>();

        current = searchID(id);

        current.getData().changetoNextLevel();
    }

    /**
     * Metodo usado para atualizar a experiencia de um jogador
     * @param id id do jogador a ser atualizado
     * @param expPoints novos pontos de experiencia do jogador
     * @throws NoSuchElementException caso o id nao exista
     * @throws NotAcceptedValue caso o valor de experiencia nao seja aceite
     */
    public void updatePlayerExperience(int id, double expPoints) throws NoSuchElementException, NotAcceptedValue {
        DoubleNode<Player> current = new DoubleNode<>();

        current = searchID(id);

        current.getData().setExperiencePoints(expPoints);
    }

    /**
     * Metodo usado para adicionar energia ao jogador
     * @param id id do jogador a adicionar a energia
     * @param energy energia a ser adicionada
     * @throws NoSuchElementException caso o id nao exista
     */
    public void addEnergy(int id, int energy) throws NoSuchElementException{
        DoubleNode<Player> current = new DoubleNode<>();

        current = searchID(id);

        current.getData().setCurrent_energy(current.getData().getCurrent_energy() + energy);
    }

    /**
     * Metodo usado para reduzir energia ao jogador
     * @param id id do jogador a ser reduzido a energia
     * @param energy energia a ser reduzida
     * @throws NoSuchElementException caso o id nao exista
     */
    public void reduceEnergy(int id, int energy) throws NoSuchElementException{
        DoubleNode<Player> current = new DoubleNode<>();

        current = searchID(id);

        current.getData().setCurrent_energy(current.getData().getCurrent_energy() - energy);
    }

    /**
     * Metodo usado para trocar a equipa do jogador
     * @param id id do jogador a ser atualizado
     * @throws NoSuchElementException caso o id nao exista
     * @throws EmptyCollectionException caso a lista esteja vazia
     * @throws NoSuchTeamExist caso a equipa nao exista
     */
    public void switchTeams(int id) throws NoSuchElementException, EmptyCollectionException, NoSuchTeamExist {
        DoubleNode<Player> current = new DoubleNode<>();

        current = searchID(id);

        if (current.getData().getTeam().equals(Teams.Giants)) {
            current.getData().setTeam(Teams.Sparks);
        } else {
            current.getData().setTeam(Teams.Giants);
        }
    }
    
    /**
     * Metodo usado para listar os jogadores
     * @return uma string com os jogadores
     */
    public String listarJogadores(){
        DoubleNode<Player> current = new DoubleNode<>();
        current = players.getHead();
        String str = "";

        while (current != null) {
            str += "\n";
            str += "Jogador \"" + current.getData().getName() + "\" -> Equipa: " + current.getData().getTeam() + " -> id: " + current.getData().getId();
            current = current.getNext();
        }
        str += "\n";
        return str;
    }

    /**
     * Metodo usado para atualizar a energia de um jogador
     * @param id id do jogador a atualizar
     * @param energy nova energia a atribuir
     * @throws NoSuchElementException caso o id nao exista
     */
    public void setNewPlayerEnergy(int id, double energy) throws NoSuchElementException {
        DoubleNode<Player> current = new DoubleNode<>();

        current = searchID(id);
        current.getData().setCurrent_energy(energy);
    }

    /**
     * Metodo da classe auxiliar usado para procurar um id na lista de jogadores
     * @param id id do jogador a procurar
     * @return o node do jogador do referente id caso exista, excessao caso nao exista
     * @throws NoSuchElementException caso o id nao exista
     */
    private DoubleNode<Player> searchID(int id) throws NoSuchElementException {
        DoubleNode<Player> current = this.players.getHead();
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
            return current;
        }
    }

    /**
     * Metodo usado para listar os jogadores por equipa
     */
    public void listarPlayersPorTeam(){
        DoubleNode<Player> current = players.getHead();
        System.out.println("Team Sparks: ");
        while (current != null){
            if (current.getData().getTeam().equals(Teams.Sparks)){
                System.out.println(this.toStringPlayer(current.getData()));
            }
            current = current.getNext();
        }
        System.out.println("______________________________");
        current = players.getHead();

        System.out.println("Team Giants: ");
        while (current != null){
            if (current.getData().getTeam().equals(Teams.Giants)){
                System.out.println(this.toStringPlayer(current.getData()));
            }
            current = current.getNext();
        }

    }

    /**
     * Metodo usado para listar os jogadores por nivel
     */
    public void listarPlayerPorNivel() {
        Player[] temp_players = new Player[players.getCount()];
        DoubleNode<Player> current = players.getHead();
        int nextObj = 0;

        while (current != null) {
            temp_players[nextObj] = current.getData();
            current = current.getNext();
            nextObj++;
        }
        int n = temp_players.length;

        for (int i = 0; i < n-1; i++) {
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (temp_players[j].getLevel() > temp_players[min_idx].getLevel()){
                    min_idx = j;
                }
            Player temp = temp_players[min_idx];
            temp_players[min_idx] = temp_players[i];
            temp_players[i] = temp;
        }
        for (int k = 0 ; k < temp_players.length && temp_players[k] != null ; k++) {
            System.out.println("id: " + temp_players[k].getId() + " -> level: " + temp_players[k].getLevel());
        }

    }
    
    /**
     * Metodo usado para listar os jogadores por portais conquistados
     * @throws EmptyCollectionException caso a lista esteja vazia
     */
    public void listarPlayerPorPortaisConquistados() throws EmptyCollectionException{
        Player[] temp_players = new Player[players.getCount()];
        DoubleNode<Player> current = players.getHead();
        int nextObj = 0;

        while (current != null) {
            temp_players[nextObj] = current.getData();
            current = current.getNext();
            nextObj++;
        }
        int n = temp_players.length;

        for (int i = 0; i < n-1; i++) {
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (temp_players[j].getnPortais() > temp_players[min_idx].getnPortais()){
                    min_idx = j;
                }
            Player temp = temp_players[min_idx];
            temp_players[min_idx] = temp_players[i];
            temp_players[i] = temp;
        }
        for (int k = 0 ; k < temp_players.length && temp_players[k] != null ; k++) {
            System.out.println("id: " + temp_players[k].getId() + " -> Numero de portais conquistados: " + temp_players[k].getLevel());
        }
    }

    /**
     * Metodo toString para printar as informaçoes de um jogador
     * @param p o jogador a ser printado
     * @return uma string com as informaçoes do jogador
     */
    private String toStringPlayer(Player p){
        return "Nome: " + p.getName() + " -> id: " + p.getId() + "\n------------------";
    }

}
