package ed_project.Interfaces;

import ed_project.Enumerations.Teams;
import ed_project.Exceptions.*;
import ed_project.Game.Interacao;
import ed_project.Game.Local;
import ed_project.GameManagement.GestaoJogadores;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public interface LocalManagement {
    public void addPortal(double lat, double lon, String name, double maxEnergy, Teams team);

    public void addConnector(double lat, double lon, double energy, int cooldown);

    public void removeLocal(int id) throws EmptyCollectionException, NoSuchElementException;

    public void addInteracoes(int id, Interacao interacao) throws NoSuchElementException;

    public void removeInteracoes(int id_player, int id_local) throws NoSuchElementException, EmptyCollectionException;

    public void updateLocalCoordinates(int id, double lat, double lon) throws NoSuchElementException;

    public void updatePortalName(int id, String name) throws NoSuchElementException;

    public void switchTeams(int id, Teams team) throws NoSuchElementException, EmptyCollectionException, NoSuchTeamExist;

    public void adicionarRotasUmSentido(Local local1, Local local2);

    public void adicionarRotasDoisSentidos(Local local1, Local local2);

    public void removerRotasUmSentido(Local local1, Local local2) throws EmptyCollectionException;

    public void removerRotasDoisSentidos(Local local1, Local local2) throws EmptyCollectionException;

    public void listarDadosGeraisPortais();

    public void listarPortaisPorEnergia() throws EmptyCollectionException;

    public void listarPortaisPorOwnership(GestaoJogadores gj);

    public void listarDadosGeraisConectores();

    public void listarConnectorMenorCooldown() throws EmptyCollectionException;

    public void printMatrix();

}
