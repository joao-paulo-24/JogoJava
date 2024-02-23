package ed_project.Interfaces;

import ed_project.Exceptions.*;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public interface PlayerManagement {
    public void addPlayer(String name, int team) throws NoSuchTeamExist;

    public void removePlayer(int id) throws NoSuchElementException, EmptyCollectionException;

    public void updatePlayer(int id, String name) throws NoSuchElementException, EmptyCollectionException;

    public void switchTeams(int id) throws NoSuchElementException, EmptyCollectionException, NoSuchTeamExist;

    public String listarJogadores();

    public void listarPlayersPorTeam();

    public void listarPlayerPorNivel();

    public void listarPlayerPorPortaisConquistados() throws EmptyCollectionException;

}
