package ed_project.JSONFiles;


import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import ed_project.DataStructures.DoubleNode;
import ed_project.Game.*;
import ed_project.GameManagement.GestaoJogadores;
import ed_project.GameManagement.GestaoLocais;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class FileExport {

    public void exportToJSONGlobal(GestaoLocais gl, GestaoJogadores gj) throws IOException {
        DoubleNode<Player> p = gj.getPlayers().getHead();
        DoubleNode<Local> l = gl.getLocais().getHead();
        DoubleNode<Local> v1 = gl.getLocais().getHead();
        JSONArray locais = new JSONArray();
        JSONArray routes = new JSONArray();
        JSONArray players = new JSONArray();
        JSONObject global = new JSONObject();

        for (int i = 0; i < gl.getLocais().getCount(); i++) {
            JSONObject object = new JSONObject();
            JSONObject coordinates = new JSONObject();
            JSONArray gameSettings = new JSONArray();
            JSONObject portalObject = new JSONObject();
            JSONObject connectorObject = new JSONObject();
            JSONObject ownership = new JSONObject();

            object.put("id", l.getData().getId());
            object.put("type", l.getData().getClass().toString());
            coordinates.put("latitude", l.getData().getLatitude());
            coordinates.put("longitude", l.getData().getLongitude());
            object.put("coordinates", coordinates);
            object.put("energy", l.getData().getEnergy());
            portalObject.put("energy", l.getData().getEnergy());
            connectorObject.put("energy", l.getData().getEnergy());
            if (l.getData() instanceof Portal) {
                Portal pt = (Portal) l.getData();
                portalObject.put("name", pt.getName());
                portalObject.put("maxEnergy", pt.getEnergiaMaxima());
                ownership.put("player", pt.getPlayer().getName());
                portalObject.put("ownership", ownership);
                gameSettings.put(portalObject);

            } else if (l.getData() instanceof Connector) {
                Connector c = (Connector) l.getData();
                connectorObject.put("cooldown", c.getCooldown());
                gameSettings.put(connectorObject);
            }
            object.put("gameSettings", gameSettings);

            locais.put(object);

            l = l.getNext();
        }

        for (int i = 0; i < gl.getMapa().size(); i++) {
            JSONObject object = new JSONObject();
            DoubleNode<Local> l1 = v1.getData().getConexoes().getHead();
            for (int j = 0; j < v1.getData().getConexoes().size(); j++) {
                object.put("from", v1.getData().getId());
                object.put("to", l1.getData().getId());
                routes.put(object);
                l1 = l1.getNext();
            }

            v1 = v1.getNext();
        }

        for (int i = 0; i < gj.getPlayers().getCount(); i++) {
            JSONObject object = new JSONObject();

            object.put("name", p.getData().getName());
            object.put("team", p.getData().getTeam().toString());
            object.put("level", p.getData().getLevel());
            object.put("experiencePoints", p.getData().getExperiencePoints());
            object.put("currentEnergy", p.getData().getCurrent_energy());
            players.put(object);

            p = p.getNext();
        }

        global.put("Locais", locais);
        global.put("Routes", routes);
        global.put("Players", players);

        FileWriter f = new FileWriter("JSONGlobal.json");
        f.write(global.toString());
        f.close();
    }

    public void exportToJSONLocals(GestaoLocais gl) throws IOException {
        DoubleNode<Local> l = gl.getLocais().getHead();
        JSONArray locais = new JSONArray();
        JSONObject global = new JSONObject();
        
        for (int i = 0; i < gl.getLocais().getCount(); i++) {
            JSONObject object = new JSONObject();
            JSONObject coordinates = new JSONObject();
            JSONArray gameSettings = new JSONArray();
            JSONObject portalObject = new JSONObject();
            JSONObject connectorObject = new JSONObject();
            JSONObject ownership = new JSONObject();

            object.put("id", l.getData().getId());
            object.put("type", l.getData().getClass().toString());
            coordinates.put("latitude", l.getData().getLatitude());
            coordinates.put("longitude", l.getData().getLongitude());
            object.put("coordinates", coordinates);
            object.put("energy", l.getData().getEnergy());
            portalObject.put("energy", l.getData().getEnergy());
            connectorObject.put("energy", l.getData().getEnergy());
            if (l.getData() instanceof Portal) {
                Portal pt = (Portal) l.getData();
                portalObject.put("name", pt.getName());
                portalObject.put("maxEnergy", pt.getEnergiaMaxima());
                ownership.put("player", pt.getPlayer().getName());
                portalObject.put("ownership", ownership);
                gameSettings.put(portalObject);

            } else if (l.getData() instanceof Connector) {
                Connector c = (Connector) l.getData();
                connectorObject.put("cooldown", c.getCooldown());
                gameSettings.put(connectorObject);
            }
            object.put("gameSettings", gameSettings);

            locais.put(object);

            l = l.getNext();
        }

        global.put("locais", locais);

        FileWriter f = new FileWriter("JSONLocals.json");
        f.write(global.toString());
        f.close();
    }

    public void exportToJSONRoutes(GestaoLocais gl) throws IOException {
        DoubleNode<Local> v1 = gl.getLocais().getHead();
        JSONArray routes = new JSONArray();
        JSONObject global = new JSONObject();

        while (v1 != null){
            
            JSONObject object = new JSONObject();
            DoubleNode<Local> l1 = v1.getData().getConexoes().getHead();
            
            while (l1 != null) {
                object.put("from", v1.getData().getId());
                object.put("to", l1.getData().getId());
                routes.put(object);
                l1 = l1.getNext();
            }

            v1 = v1.getNext();
        }

        global.put("routes", routes);

        FileWriter f = new FileWriter("JSONRoutes.json");
        f.write(global.toString());
        f.close();
    }

    public void exportToJSONPlayers(GestaoJogadores gj) throws IOException {
        DoubleNode<Player> p = gj.getPlayers().getHead();
        JSONArray players = new JSONArray();
        JSONObject global = new JSONObject();

        for (int i = 0; i < gj.getPlayers().getCount(); i++) {
            JSONObject object = new JSONObject();

            object.put("name", p.getData().getName());
            object.put("team", p.getData().getTeam().toString());
            object.put("level", p.getData().getLevel());
            object.put("experiencePoints", p.getData().getExperiencePoints());
            object.put("currentEnergy", p.getData().getCurrent_energy());
            players.put(object);

            p = p.getNext();
        }

        global.put("players", players);

        FileWriter f = new FileWriter("JSONPlayers.json");
        f.write(global.toString());
        f.close();
    }
}
