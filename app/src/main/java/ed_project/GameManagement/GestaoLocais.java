package ed_project.GameManagement;

import ed_project.DataStructures.DoubleLinkedUnorderedList;
import ed_project.DataStructures.DoubleNode;
import ed_project.DataStructures.Network;
import ed_project.Enumerations.Teams;
import ed_project.Exceptions.*;
import ed_project.Game.*;
import ed_project.Interfaces.LocalManagement;

import java.util.Iterator;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class GestaoLocais implements LocalManagement {
    /**
     * Lista de locais
     */
    private DoubleLinkedUnorderedList<Local> locais;

    /**
     * Mapa do jogo
     */
    private Network<Local> mapa;

    /**
     * Metodo construtor para a classe Gestao de locais
     */
    public GestaoLocais() {
        this.locais = new DoubleLinkedUnorderedList<>();
        this.mapa = new Network<>();
    }

    public DoubleLinkedUnorderedList<Local> getLocais() {
        return locais;
    }

    public void setLocais(DoubleLinkedUnorderedList<Local> locais) {
        this.locais = locais;
    }

    public Network<Local> getMapa() {
        return mapa;
    }

    public void setMapa(Network<Local> mapa) {
        this.mapa = mapa;
    }

    /**
     * Método usado para adicionar um portal à lista de locais
     * @param lat latitude do portal
     * @param lon longitude do portal
     * @param name nome do portal
     * @param maxEnergy energia maxima do portal
     * @param team equipa do portal
     */
    public void addPortal(double lat, double lon, String name, double maxEnergy, Teams team) {
        boolean exist;
        if (verifyCoord(lat, lon)) {
            exist = verifyNotRepeatCoordinates(lat, lon);
            if (exist == true) {
                System.out.println("\nJá existe outro local com as mesmas coordenadas. O Portal não foi criado.");
            } else {
                Portal local_temp = new Portal(0, lat, lon, null, name, maxEnergy, team);
                locais.addToRear(local_temp);
                mapa.addVertex(local_temp);
            }
        }
    }

    /**
     * Metodo para adicionar um conector à lista de locais
     * @param lat latitude do conector
     * @param lon longitude do conector
     * @param energy energia do conector
     * @param cooldown cooldown do conector
     */
    public void addConnector(double lat, double lon, double energy, int cooldown) {
        boolean exist;
        if (verifyCoord(lat, lon)) {
            exist = verifyNotRepeatCoordinates(lat, lon);
            if (exist == true) {
                System.out.println("\nJá existe outro local com as mesmas coordenadas. O conector não foi criado.");
            } else {
                Connector local_temp = new Connector(energy, lat, lon, cooldown);
                locais.addToRear(local_temp);
                mapa.addVertex(local_temp);
            }
        }
    }

    /**
     * Metodo para remover um local da lista de locais
     * @param id id do local a ser removido
     * @throws EmptyCollectionException caso a lista esteja vazia
     * @throws NoSuchElementException caso o id passado nao exista
     */
    public void removeLocal(int id) throws EmptyCollectionException, NoSuchElementException {
        DoubleNode<Local> current = new DoubleNode<>();

        current = this.searchID(id);

        this.locais.remove(current.getData());
        this.mapa.removeVertex(current.getData());
    }

    /**
     * Método usado para adicionar interaçoes à lista de interaçoes do portal
     * @param id id do local a ser procurado
     * @param interacao interação a adicionar à lista
     * @throws NoSuchElementException caso o id passado nao exista
     */
    public void addInteracoes(int id, Interacao interacao) throws NoSuchElementException{
        DoubleNode<Local> current = new DoubleNode<>();

        current = this.searchID(id);

        ((Connector)current.getData()).addInteracoes(interacao);
    }

    /**
     * Metodo usado para remover interações da lista de interações do portal
     * @param id_player id do jogador da interação a remover
     * @param id_local id do portal que vai se remover a interaçao
     * @throws NoSuchElementException caso o id passado nao exista
     * @throws EmptyCollectionException caso a lista esteja vazia
     */
    public void removeInteracoes(int id_player, int id_local) throws NoSuchElementException, EmptyCollectionException{
        DoubleNode<Local> current = new DoubleNode<>();

        current = this.searchID(id_local);

        ((Connector)current.getData()).removeInteraction(id_player);
    }

    /**
     * Metodo usado para atualizar as coordenadas do local
     * @param id id do local a ser atualizado
     * @param lat nova latitude
     * @param lon nova longitude
     * @throws NoSuchElementException caso o id passado nao exista
     */
    public void updateLocalCoordinates(int id, double lat, double lon) throws NoSuchElementException {
        if (verifyCoord(lat, lon)) {
            DoubleNode<Local> current = new DoubleNode<>();

            current = this.searchID(id);

            current.getData().setLatitude(lat);
            current.getData().setLongitude(lon);
        }
    }

    /**
     * Metodo usado para atualizar o nome do portal
     * @param id id do portal a ser atualizado
     * @param name novo nome a ser atribuido
     * @throws NoSuchElementException caso o id passado nao exista
     */
    public void updatePortalName(int id, String name) throws NoSuchElementException {
        DoubleNode<Local> current = new DoubleNode<>();

        current = this.searchID(id);

        if (current.getData() instanceof Portal) {
            Portal p1 = (Portal) current.getData();
            p1.setName(name);
            current.setData(p1);
        }
    }

    /**
     * Metodo usado para trocar a equipa do portal
     * @param id id do portal a trocar a equipa
     * @param team nova equipa a ser atribuida
     * @throws NoSuchElementException caso nao exista o id passado
     * @throws EmptyCollectionException caso a lista esteja vazia
     * @throws NoSuchTeamExist caso a equipa nao exista
     */
    public void switchTeams(int id, Teams team) throws NoSuchElementException, EmptyCollectionException, NoSuchTeamExist {
        DoubleNode<Local> current = new DoubleNode<>();

        current = searchID(id);
        ((Portal)current.getData()).setTeam(team);

    }

    /**
     * Metodo usado para atribuir um novo ownership
     * @param id id do local a ser atribuido o novo ownership
     * @param ownership novo jogador que vai ocupar a posicao de ownership
     * @throws NoSuchElementException caso o id passado nao exista
     */
    public void setNewOwnership(int id, Player ownership) throws NoSuchElementException {
        DoubleNode<Local> current = new DoubleNode<>();

        current = searchID(id);
        ((Portal)current.getData()).setOwnership(ownership);
    }

    /**
     * Metodo usado para atribuir um uma soma de energia ao local
     * @param id id do local a atualizar
     * @param energy nova energia a atribuir
     * @throws NoSuchElementException caso o id passado nao exista
     */
    public void addEnergyLocal(int id, double energy) throws NoSuchElementException{
        DoubleNode<Local> current = new DoubleNode<>();

        current = searchID(id);

        current.getData().setEnergy(current.getData().getEnergy() + energy);
    }

    /**
     * Metodo usado para atribuir um novo valor de energia ao local
     * @param id id do local a ser atualizado
     * @param energy nova energia a atribuir
     * @throws NoSuchElementException caso o id passado nao exista
     */
    public void setNewPortalEnergy(int id, double energy) throws NoSuchElementException{
        DoubleNode<Local> current = new DoubleNode<>();

        current = searchID(id);
        current.getData().setEnergy(energy);
    }

    /**
     * Metodo usado para adicionar uma rota de um sentido
     * @param local1 local de origem da rota
     * @param local2 local de destino da rota
     */
    public void adicionarRotasUmSentido(Local local1, Local local2){
        boolean exist = hasRoute(local1, local2);
        if (exist == true) {
            System.out.println("Já existe esta rota");
        } else {
            mapa.addOneEdge(local1, local2, Math.sqrt((local1.getLatitude() - local2.getLatitude()) * (local1.getLatitude() - local2.getLatitude()) + (local1.getLongitude() - local2.getLongitude()) * (local1.getLongitude() - local2.getLongitude())));
            local1.addConections(local2);
        }
    }

    /**
     * Metodo usado para adicionar uma rota de dois sentidos
     * @param local1 um vertice da rota
     * @param local2 outro vertice da rota
     */
    public void adicionarRotasDoisSentidos(Local local1, Local local2){  
        boolean exist = hasRoute(local1, local2);
        boolean exist2 = hasRoute(local2, local1);

        if (exist == true && exist2 == true) {
            System.out.println("Essas rotas já existem");
        } else if (exist == true && exist2 == false) {
            mapa.addOneEdge(local2, local1, Math.sqrt((local1.getLatitude() - local2.getLatitude()) * (local1.getLatitude() - local2.getLatitude()) + (local1.getLongitude() - local2.getLongitude()) * (local1.getLongitude() - local2.getLongitude())) );  
            local2.addConections(local1);
        } else if (exist == false && exist2 == true) {
            mapa.addOneEdge(local1, local2, Math.sqrt((local1.getLatitude() - local2.getLatitude()) * (local1.getLatitude() - local2.getLatitude()) + (local1.getLongitude() - local2.getLongitude()) * (local1.getLongitude() - local2.getLongitude())) ); 
            local1.addConections(local2);
        } else if (exist == false && exist2 == false) {
            mapa.addTwoEdge(local1, local2, Math.sqrt((local1.getLatitude() - local2.getLatitude()) * (local1.getLatitude() - local2.getLatitude()) + (local1.getLongitude() - local2.getLongitude()) * (local1.getLongitude() - local2.getLongitude())) ); 
            local1.addConections(local2);
            local2.addConections(local1);
        }
    }

    /**
     * Metodo usar para remover uma rota de um sentido
     * @param local1 local da origem da rota
     * @param local2 local de destino da rota
     * @throws EmptyCollectionException caso a lista esteja vazia
     */
    public void removerRotasUmSentido(Local local1, Local local2) throws EmptyCollectionException{
        boolean exist = hasRoute(local1, local2);

        if (exist == false) {
            System.out.println("Não existe uma rota entre estes dois locais");
        } else {
            mapa.removeOneEdgeWeight(local1, local2, 0);
            mapa.removeOneEdge(local1, local2);
            local1.removeConections(local2);
        }
    }

    /**
     * Metodo usado para remover uma rota de dois sentidos
     * @param local1 um vertice da rota
     * @param local2 outro vertice da rota
     * @throws EmptyCollectionException caso a lista esteja vazia
     */
    public void removerRotasDoisSentidos(Local local1, Local local2) throws EmptyCollectionException{
        boolean exist = hasRoute(local1, local2);
        boolean exist2 = hasRoute(local2, local1);

        if (exist == false && exist2 == false) {
            System.out.println("Não existem rotas a serem removidas");
        } else if (exist == true && exist2 == false) {
            mapa.removeOneEdgeWeight(local1, local2, 0);
            mapa.removeEdge(local1, local2);
            local1.removeConections(local2);
        } else if (exist == false && exist2 == true) {
            mapa.removeOneEdgeWeight(local2, local1, 0);
            mapa.removeEdge(local2, local1);
            local2.removeConections(local1);
        } else if (exist == true && exist2 == true) {
            mapa.removeTwoEdgeWeight(local1, local2, 0);
            mapa.removeEdge(local1, local2);
            mapa.removeEdge(local2, local1);
            local1.removeConections(local2);
            local2.removeConections(local1);
        }
    }

    /**
     * Metodo usado para listar os dados gerais dos portais
     */
    public void listarDadosGeraisPortais(){
        DoubleNode<Local> current = locais.getHead();
        DoubleNode<Local> current2 = locais.getHead();
        int nPortais = 0;
        int nextObj = 0;

        while (current != null) {
            if (current.getData() instanceof Portal) {
                nPortais++;
                current = current.getNext();
            } else {
                current = current.getNext();
            }
        }

        Portal[] temp_portais = new Portal[nPortais];

        while (current2 != null) {
            if (current2.getData() instanceof Portal) {
                temp_portais[nextObj] = (Portal) current2.getData();
                nextObj++;
                current2 = current2.getNext();
            } else {
                current2 = current2.getNext();
            }
        }

        for (int k = 0 ; k < temp_portais.length && temp_portais[k] != null ; k++) {
            System.out.println("Nome: " + temp_portais[k].getName() + " -> id: " + temp_portais[k].getId());
        }
    }

    /**
     * Metodo usado para listar os portais por valor de energia
     * @throws EmptyCollectionException caso a lista esteja vazia
     */
    public void listarPortaisPorEnergia() throws EmptyCollectionException {
        DoubleNode<Local> current = locais.getHead();
        DoubleNode<Local> current2 = locais.getHead();
        int nPortais = 0;
        int nextObj = 0;

        while (current != null) {
            if (current.getData() instanceof Portal) {
                nPortais++;
                current = current.getNext();
            } else {
                current = current.getNext();
            }
        }

        Portal[] temp_portais = new Portal[nPortais];

        while (current2 != null) {
            if (current2.getData() instanceof Portal) {
                temp_portais[nextObj] = (Portal) current2.getData();
                nextObj++;
                current2 = current2.getNext();
            } else {
                current2 = current2.getNext();
            }
        }
        int n = temp_portais.length;

        for (int i = 0; i < n-1; i++) {
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (temp_portais[j].getEnergy() > temp_portais[min_idx].getEnergy()){
                    min_idx = j;
                }
            Portal temp = temp_portais[min_idx];
            temp_portais[min_idx] = temp_portais[i];
            temp_portais[i] = temp;
        }
        for (int k = 0 ; k < temp_portais.length && temp_portais[k] != null ; k++) {
            System.out.println("Nome: " + temp_portais[k].getName() + " -> id: " + temp_portais[k].getId() + " -> Energia: " + temp_portais[k].getEnergy());
        }
    }

    /**
     * Metodo usado para listar os portais por ownership
     * @param gj classe que vai permitir criar um array copia da lista
     */
    public void listarPortaisPorOwnership(GestaoJogadores gj) {
        DoubleNode<Local> current = locais.getHead();
        DoubleNode<Local> current2 = locais.getHead();
        int nPortais = 0;
        int nextObj = 0;

        while (current != null) {
            if (current.getData() instanceof Portal) {
                nPortais++;
                current = current.getNext();
            } else {
                current = current.getNext();
            }
        }

        Portal[] temp_portais = new Portal[nPortais];

        while (current2 != null) {
            if (current2.getData() instanceof Portal) {
                temp_portais[nextObj] = (Portal) current2.getData();
                nextObj++;
                current2 = current2.getNext();
            } else {
                current2 = current2.getNext();
            }
        }

        for (int k = 0 ; k < temp_portais.length && temp_portais[k] != null ; k++) {
            System.out.println("Nome " + temp_portais[k].getName() + ", id: " + temp_portais[k].getId() + " -> Owner: " + temp_portais[k].getOwnership().getName() 
            + " (id " + temp_portais[k].getOwnership().getId() + " - " + temp_portais[k].getOwnership().getTeam().toString() + ")");
        }
    }

    /**
     * Metodo usado para listar os dados gerais dos conectores
     */
    public void listarDadosGeraisConectores() {
        DoubleNode<Local> current = locais.getHead();
        DoubleNode<Local> current2 = locais.getHead();
        int nConectors = 0;
        int nextObj = 0;

        while (current != null) {
            if (current.getData() instanceof Connector) {
                nConectors++;
                current = current.getNext();
            } else {
                current = current.getNext();
            }
        }

        Connector[] temp_conector = new Connector[nConectors];

        while (current2 != null) {
            if (current2.getData() instanceof Connector) {
                temp_conector[nextObj] = (Connector) current2.getData();
                nextObj++;
                current2 = current2.getNext();
            } else {
                current2 = current2.getNext();
            }
        }

        for (int k = 0 ; k < temp_conector.length && temp_conector[k] != null ; k++) {
            System.out.println("Conector id: " + temp_conector[k].getId() + " -> Energia: " + temp_conector[k].getEnergy());
        }
    }

    /**
     * Metodo usado para listar os conectores por menor cooldown
     * @throws EmptyCollectionException caso a lista esteja vazia
     */
    public void listarConnectorMenorCooldown() throws EmptyCollectionException{
        DoubleNode<Local> current = locais.getHead();
        DoubleNode<Local> current2 = locais.getHead();
        int nConectors = 0;
        int nextObj = 0;

        while (current != null) {
            if (current.getData() instanceof Connector) {
                nConectors++;
                current = current.getNext();
            } else {
                current = current.getNext();
            }
        }

        Connector[] temp_conector = new Connector[nConectors];

        while (current2 != null) {
            if (current2.getData() instanceof Connector) {
                temp_conector[nextObj] = (Connector) current2.getData();
                nextObj++;
                current2 = current2.getNext();
            } else {
                current2 = current2.getNext();
            }
        }

        int n = temp_conector.length;

        for (int i = 0; i < n-1; i++) {
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (temp_conector[j].getEnergy() < temp_conector[min_idx].getEnergy()){
                    min_idx = j;
                }
            Connector temp = temp_conector[min_idx];
            temp_conector[min_idx] = temp_conector[i];
            temp_conector[i] = temp;
        }

        for (int k = 0 ; k < temp_conector.length && temp_conector[k] != null ; k++) {
            System.out.println("Conector id: " + temp_conector[k].getId() + " -> Cooldown: " + temp_conector[k].getCooldown());
        }

    }

    /**
     * Método usado para printar a matriz de adjacencia do mapa
     */
    public void printMatrix() {
        System.out.println("Esta é a matriz adjacência do mapa do seu jogo. Acesse https://graphonline.ru/en/create_graph_by_matrix para uma visualização facilitada da mesma.");
        String str = "";
        double[][] matrix = mapa.getAdjMatrix();
        for (int lin = 0 ; lin < locais.getCount() ; lin++) {
            for (int col = 0 ; col < locais.getCount() ; col++) {
                if (col == locais.getCount()-1) {
                    str += matrix[lin][col];
                } else {
                    str += matrix[lin][col] + ",";
                }
            }
            str += "\n";
        }
        System.out.println(str);
    }

    /**
     * Metodo usado para obter o caminho mais curto entre dois pontos
     * @param id1 primeiro ponto
     * @param id2 segundo ponto
     * @return string referente à rota pretendida
     * @throws NoSuchElementException caso os ids nao existam
     * @throws EmptyCollectionException caso a lista esteja vazia
     */
    public String caminhoMaisCurto(int id1, int id2) throws NoSuchElementException, EmptyCollectionException{
        String str = "";
        Iterator<Local> locaisIt;

        locaisIt = mapa.iteratorShortestPath(id1, id2);
        System.out.println("Rota do caminho mais curto: ");
        while (locaisIt.hasNext()) {
            str += locaisIt.next().getId() + ", ";
        }
        return str;
    }

    /**
     * Metodo usado para obter o caminho mais curto entre dois pontos passando obrigatoriamente por um
     * @param id1 primeiro ponto
     * @param id2 ponto por que tem de passar
     * @param id3 segundo ponto
     * @return string referente à rota pretendida
     * @throws EmptyCollectionException caso a lista esteja vazia
     */
    public String camMaisCurtoPor1Vertice(int id1, int id2, int id3) throws EmptyCollectionException {
        String str = "";
        Iterator<Local> locaisIt;
        Iterator<Local> locaisIt2;

        locaisIt = mapa.iteratorShortestPath(id1, id2);
        System.out.println("Rota do caminho mais curto: ");
        while (locaisIt.hasNext()) {
            str += locaisIt.next().getId() + ", ";
        }
        locaisIt2 = mapa.iteratorShortestPath(id2, id3);
        while (locaisIt2.hasNext()) {
            str += locaisIt2.next().getId() + ", ";
        }

        return str;
    }

    /**
     * Metodo da classe auxiliar que permite saber se ja existe rota entre dois pontos
     * @param local1 local do primeiro vertice
     * @param local2 local do segundo vertice
     * @return true caso haja rota entre os dois vertices, falso caso contrario
     */
    private boolean hasRoute(Local local1, Local local2) {
        DoubleNode<Local> current = local1.getConexoes().getHead();
        boolean found = false;

        while (current != null && !found) {
            if (current.getData() == local2) {
                found = true;
            } else {
                current = current.getNext();
            }
        }
        
        return found;
    }

    /**
     * Metodo da classe auxiliar usado para obter o node da lista referente ao id passado 
     * @param id id do local que se quer obter o node
     * @return o node caso exista, excessao caso contrario
     * @throws NoSuchElementException caso o id nao exista
     */
    private DoubleNode<Local> searchID(int id) throws NoSuchElementException {
        DoubleNode<Local> current = this.locais.getHead();
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
            return current;
        }
    }

    /**
     * Metodo da classe auxiliar usado para confirmar que as coordenadas se encontram no intervalo de valores permitidos
     * @param lat latitude do local
     * @param lon longitude do local
     * @return true caso esteja de acordo com o intervalo, false caso contrario
     */
    private boolean verifyCoord(double lat, double lon) {
        return (lat >= -90 && lat <= 90 && lon >= -180 && lon <= 180);
    }

    /**
     * Metodo da classe auxiliar que permite confirmar se as coordenadas passadas ja nao existem em outro local
     * @param lat latitude do local
     * @param lon longitude do local
     * @return true caso as coordenadas sejam unicas, false caso contrario
     */
    private boolean verifyNotRepeatCoordinates(double lat, double lon){
        DoubleNode<Local> current = this.locais.getHead();
        boolean found = false;

        while (current != null && !found) {
            if (current.getData().getLatitude() == lat && current.getData().getLatitude() == lon) {
                found = true;
            } else {
                current = current.getNext();
            }
        }
        
        return found;
    }

}
