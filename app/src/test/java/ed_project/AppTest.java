package ed_project;

import org.junit.jupiter.api.Test;

import ed_project.Enumerations.Teams;
import ed_project.Exceptions.EmptyCollectionException;
import ed_project.Exceptions.NoSuchElementException;
import ed_project.Exceptions.NoSuchTeamExist;
import ed_project.Exceptions.NotAcceptedValue;
import ed_project.Game.Connector;
import ed_project.Game.Player;
import ed_project.Game.Portal;
import ed_project.GameManagement.GestaoJogadores;
import ed_project.GameManagement.GestaoLocais;
import ed_project.GameManagement.Play;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

class AppTest {
    
    @Test
    void isPlayerAdded() throws NoSuchTeamExist{
        GestaoJogadores gj = new GestaoJogadores();
        gj.addPlayer("player1", 1);
        assertEquals(1, gj.getPlayers().getCount());
    }

    @Test
    void isPlayerRemoved() throws NoSuchTeamExist, NoSuchElementException, EmptyCollectionException{
        GestaoJogadores gj = new GestaoJogadores();
        gj.addPlayer("player1", 1);
        gj.addPlayer("player2", 2);
        gj.removePlayer(1);
        assertEquals(1, gj.getPlayers().getCount());
    }

    @Test
    void isPortalAdded() {
        GestaoLocais gl = new GestaoLocais();
        gl.addPortal(10, 10, "p1", 10, Teams.Giants);
        assertEquals(1, gl.getLocais().getCount());
    }

    @Test
    void isPortalRemoved() throws NoSuchTeamExist, NoSuchElementException, EmptyCollectionException{
        GestaoLocais gl = new GestaoLocais();
        gl.addPortal(10, 10, "p1", 10, Teams.Giants);
        gl.addPortal(10, 20, "p2", 10, Teams.Sparks);
        gl.removeLocal(1);
        assertEquals(1, gl.getLocais().getCount());
    }

    @Test
    void arePortalCoordinatesChanging () throws NoSuchElementException{
        GestaoLocais gl = new GestaoLocais();
        Portal portal = new Portal(0, 10, 10, null, "l1", 200, Teams.Sparks);
        gl.getLocais().addToRear(portal);
        gl.updateLocalCoordinates(0, 30, 30);
        assertEquals(30, gl.getLocais().getHead().getData().getLatitude());
        assertEquals(30, gl.getLocais().getHead().getData().getLongitude());
    }

    @Test
    void isPortalTeamSwitching() throws NoSuchElementException, EmptyCollectionException, NoSuchTeamExist{
        GestaoLocais gl = new GestaoLocais();
        Portal portal = new Portal(0, 10, 10, null, "l1", 200, Teams.Sparks);
        gl.getLocais().addToRear(portal);
        gl.switchTeams(0, Teams.Giants);
        Portal temp = (Portal) gl.getLocais().getHead().getData();
        assertEquals(Teams.Giants, temp.getTeam());
    }

    @Test
    void isPortalOwnershipChanging() throws NoSuchElementException{
        GestaoLocais gl = new GestaoLocais();
        GestaoJogadores gj = new GestaoJogadores();
        Player player = new Player("j1", Teams.Sparks);
        Player player2 = new Player("j2", Teams.Sparks);
        Portal portal = new Portal(0, 10, 10, null, "l1", 200, Teams.Sparks);    
        gl.getLocais().addToRear(portal);
        gj.getPlayers().addToRear(player);
        gj.getPlayers().addToRear(player2);
        gl.setNewOwnership(0, player2);;
        Portal temp = (Portal) gl.getLocais().getHead().getData();
        assertEquals(player2, temp.getOwnership());
    }

    @Test
    void isPortalUpdatingName() throws NoSuchElementException {
        GestaoLocais gl = new GestaoLocais();
        Portal portal = new Portal(0, 10, 10, null, "l1", 200, Teams.Sparks);
        gl.getLocais().addToRear(portal);
        gl.updatePortalName(0, "alterado");
        Portal temp = (Portal) gl.getLocais().getHead().getData();
        assertEquals("alterado", temp.getName());
    }

    @Test
    void isPortalAddedWithSameCoordinates() throws NoSuchTeamExist, NoSuchElementException, EmptyCollectionException{
        GestaoLocais gl = new GestaoLocais();
        gl.addPortal(10, 10, "p1", 10, Teams.Giants);
        gl.addPortal(10, 10, "p2", 10, Teams.Sparks);
        assertEquals(1, gl.getLocais().getCount(), "As coordenadas dos portais devem ser diferentes");
    }

    @Test
    void isConnectorAdded() {
        GestaoLocais gl = new GestaoLocais();
        gl.addConnector(40, 40, 30, 2);
        assertEquals(1, gl.getLocais().getCount());
    }

    @Test
    void isConnectorRemoved() throws NoSuchTeamExist, NoSuchElementException, EmptyCollectionException{
        GestaoLocais gl = new GestaoLocais();
        gl.addConnector(40, 40, 30, 2);
        gl.addConnector(50, 80, 30, 2);
        gl.removeLocal(1);
        assertEquals(1, gl.getLocais().getCount());
    }

    @Test
    void isConnectorCoordinatesUpdating() throws NoSuchElementException{
        GestaoLocais gl = new GestaoLocais();
        Connector connector = new Connector(150, 20, 20, 3);
        gl.getLocais().addToRear(connector);
        gl.updateLocalCoordinates(0, 30, 30);
        assertEquals(30, gl.getLocais().getHead().getData().getLatitude());
        assertEquals(30, gl.getLocais().getHead().getData().getLongitude());
    }

    @Test
    void isConnectorAddedWithSameCoordinates() throws NoSuchTeamExist, NoSuchElementException, EmptyCollectionException{
        GestaoLocais gl = new GestaoLocais();
        gl.addConnector(40, 40, 30, 2);
        gl.addConnector(40, 40, 55, 10);
        assertEquals(1, gl.getLocais().getCount(), "As coordenadas dos conectores devem ser diferentes");
    }

    @Test
    void isPlayerConquering() throws NoSuchTeamExist, NumberFormatException, EmptyCollectionException, NoSuchElementException, IOException, NotAcceptedValue{
        GestaoLocais gl = new GestaoLocais();
        GestaoJogadores gj = new GestaoJogadores();
        Player player = new Player("j1", Teams.Sparks);
        Portal portal = new Portal(0, 10, 10, null, "l1", 10, Teams.Neutro);
        player.setCurrent_energy(100);
        player.setLocalAtual(portal);
        Play p = new Play();
        gl.getLocais().addToRear(portal);
        gj.getPlayers().addToRear(player);
        p.setGj(gj);
        p.setGl(gl);
        p.setControlled_player((Player) gj.getPlayers().getHead().getData());
        p.setCurrent_position(gl.getLocais().first());
        p.attackPortal();
        Portal x = (Portal) p.getGl().getLocais().getHead().getData();
        assertEquals(player, x.getOwnership(), "O portal deve ser do jogador p1");
    }

    @Test
    void isPlayerRefilling() throws NoSuchElementException, EmptyCollectionException, NoSuchTeamExist, NotAcceptedValue{
        GestaoLocais gl = new GestaoLocais();
        GestaoJogadores gj = new GestaoJogadores();
        Player player = new Player("j1", Teams.Sparks);
        Connector connector = new Connector(100, 20, 20, 5);
        player.setCurrent_energy(0);
        player.setLocalAtual(connector);
        Play p = new Play();
        gl.getLocais().addToRear(connector);
        gj.getPlayers().addToRear(player);
        p.setGj(gj);
        p.setGl(gl);
        p.setControlled_player((Player) gj.getPlayers().getHead().getData());
        p.setCurrent_position(gl.getLocais().first());
        p.refillEnergy();
        Player player1 = (Player)gj.getPlayers().getHead().getData();
        assertEquals(100, player1.getCurrent_energy());
    }

    @Test
    void isPortalCharging() throws NoSuchElementException, EmptyCollectionException, NoSuchTeamExist, NotAcceptedValue{
        GestaoLocais gl = new GestaoLocais();
        GestaoJogadores gj = new GestaoJogadores();
        Player player = new Player("j1", Teams.Sparks);
        Portal portal = new Portal(1, 10, 10, null, "l1", 200, Teams.Sparks);
        player.setCurrent_energy(100);
        player.setLocalAtual(portal);
        Play p = new Play();
        gl.getLocais().addToRear(portal);
        gj.getPlayers().addToRear(player);
        p.setGj(gj);
        p.setGl(gl);
        p.setControlled_player((Player) gj.getPlayers().getHead().getData());
        p.setCurrent_position(gl.getLocais().first());
        p.chargePortal(100);
        Portal x = (Portal)p.getGl().getLocais().getHead().getData();
        assertEquals(101, x.getEnergy());
    }

    @Test
    void isPlayerOnCorrectLocal() throws EmptyCollectionException{
        GestaoLocais gl = new GestaoLocais();
        GestaoJogadores gj = new GestaoJogadores();
        Player player = new Player("j1", Teams.Sparks);
        Portal portal = new Portal(1, 10, 10, null, "l1", 200, Teams.Sparks);
        player.setCurrent_energy(100);
        player.setLocalAtual(portal);
        Play p = new Play();
        gl.getLocais().addToRear(portal);
        gj.getPlayers().addToRear(player);
        p.setGj(gj);
        p.setGl(gl);
        p.setControlled_player((Player) gj.getPlayers().getHead().getData());
        p.setCurrent_position(gl.getLocais().first());
        Player player1 = (Player)gj.getPlayers().getHead().getData();
        assertEquals(portal, player1.getLocalAtual());
        assertEquals(portal, p.getCurrent_position());
    } 
}