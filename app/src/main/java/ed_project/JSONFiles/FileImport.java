package ed_project.JSONFiles;

import java.io.*;
import java.text.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ed_project.DataStructures.DoubleNode;
import ed_project.Enumerations.Teams;
import ed_project.Exceptions.*;
import ed_project.Game.Connector;
import ed_project.Game.Local;
import ed_project.Game.Player;
import ed_project.Game.Portal;
import ed_project.GameManagement.GestaoJogadores;
import ed_project.GameManagement.GestaoLocais;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class FileImport {
    public void importFromJSON(String filename, GestaoJogadores gj, GestaoLocais gl)
            throws FileNotFoundException, IOException, InvalidRoute, NotAcceptedValue, ParseException,
            org.json.simple.parser.ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject obj = (JSONObject) jsonParser.parse(new FileReader(filename));
        JSONArray l = (JSONArray) obj.get("Locais");
        JSONArray p = (JSONArray) obj.get("Players");
        JSONArray r = (JSONArray) obj.get("Routes");

        for (Object player : p) {
            JSONObject json = (JSONObject) player;
            String name = (String) json.get("name");
            String team = (String) json.get("team");
            Long level = (Long) json.get("level");
            Long ep = (Long) json.get("experiencePoints");
            Double ce = (Double) json.get("currentEnergy");
            Player p1 = new Player(name, null);

            if (team.equals("Sparks"))
                p1.setTeam(Teams.Sparks);
            else if (team.equals("Giants"))
                p1.setTeam(Teams.Giants);

            int lvl = level.intValue();
            double expp = ep.doubleValue();
            p1.setCurrent_energy(ce);
            p1.setExperiencePoints(expp);
            p1.setLevel(lvl);
            gj.getPlayers().addToRear(p1);
        }

        for (Object local : l) {
            JSONObject json = (JSONObject) local;
            Long id = (Long) json.get("id");
            JSONObject coord = (JSONObject) json.get("coordinates");
            Long latitude = (Long) coord.get("latitude");
            Long longitude = (Long) coord.get("longitude");
            JSONArray gs = (JSONArray) json.get("gameSettings");
            int id1 = id.intValue();
            double lat = latitude.doubleValue();
            double lon = longitude.doubleValue();
            if (((String) json.get("type")).equals("class ed_project.Game.Portal")) {
                for (Object gset : gs) {
                    JSONObject gameset = (JSONObject) gset;
                    Long energy = (Long) gameset.get("energy");
                    String name = (String) gameset.get("name");
                    Long energiaMaxima = (Long) gameset.get("maxEnergy");
                    JSONObject os = (JSONObject) gameset.get("ownership");
                    String ownership = (String) os.get("player");
                    DoubleNode<Player> playerTemp = gj.getPlayers().getHead();

                    boolean found = false;

                    while (playerTemp != null && found == false) {
                        if (playerTemp.getData().getName().equals(ownership)) {
                            found = true;
                        } else {
                            playerTemp = playerTemp.getNext();
                        }
                    }

                    Portal p1 = new Portal(id1, lat, lon, null, name, energiaMaxima, null);
                    double en = energy.doubleValue();
                    double me = energiaMaxima.doubleValue();
                    p1.setEnergy(en);
                    p1.setEnergiaMaxima(me);

                    if (found == true) {
                        p1.setOwnership(playerTemp.getData());
                        p1.setTeam(playerTemp.getData().getTeam());
                    }

                    gl.getLocais().addToRear(p1);

                }
            } else if (((String) json.get("type")).equals("class ed_project.Game.Connector")) {
                for (Object gset : gs) {
                    JSONObject gameset = (JSONObject) gset;
                    Long energy = (Long) gameset.get("energy");
                    Long cooldown = (Long) gameset.get("cooldown");
                    int cd = cooldown.intValue();
                    Connector c1 = new Connector(id1, lat, lon, cd);
                    double en = energy.doubleValue();
                    c1.setEnergy(en);
                    gl.getLocais().addToRear(c1);
                }
            }
        }

        for (Object route : r) {
            JSONObject json = (JSONObject) route;
            Long from = (Long) json.get("from");
            Long to = (Long) json.get("to");
            DoubleNode<Local> localTemp = gl.getLocais().getHead();
            boolean found = false;

            while (localTemp != null && found == false) {
                if (localTemp.getData().getId() == from) {
                    found = true;
                } else {
                    localTemp = localTemp.getNext();
                }
            }

            DoubleNode<Local> localTemp1 = gl.getLocais().getHead();
            boolean found1 = false;

            while (localTemp1 != null && found1 == false) {
                if (localTemp1.getData().getId() == to) {
                    found1 = true;
                } else {
                    localTemp1 = localTemp1.getNext();
                }
            }

            if (found && found1) {
                gl.adicionarRotasUmSentido(localTemp.getData(), localTemp1.getData());
            }
        }
    }

    public void importPlayersFromJSON(String filename, GestaoJogadores gj)
            throws FileNotFoundException, IOException, NotAcceptedValue, org.json.simple.parser.ParseException {

        JSONParser jsonParser = new JSONParser();
        JSONObject obj = (JSONObject) jsonParser.parse(new FileReader(filename));
        JSONArray p = (JSONArray) obj.get("players");

        for (Object player : p) {
            JSONObject json = (JSONObject) player;
            String name = (String) json.get("name");
            String team = (String) json.get("team");
            Long level = (Long) json.get("level");
            Long ep = (Long) json.get("experiencePoints");
            Long ce = (Long) json.get("currentEnergy");
            Player p1 = new Player(name, null);

            if (team.equals("Sparks"))
                p1.setTeam(Teams.Sparks);
            else if (team.equals("Giants"))
                p1.setTeam(Teams.Giants);

            int lvl = level.intValue();
            double expp = ep.doubleValue();
            double curen = ce.doubleValue();
            p1.setCurrent_energy(curen);
            p1.setExperiencePoints(expp);
            p1.setLevel(lvl);
            gj.getPlayers().addToRear(p1);
        }
    }

    public void importLocalsFromJSON(String filename, GestaoJogadores gj, GestaoLocais gl)
            throws FileNotFoundException, IOException, org.json.simple.parser.ParseException, NotAcceptedValue {
        JSONParser jsonParser = new JSONParser();
        JSONObject obj = (JSONObject) jsonParser.parse(new FileReader(filename));
        JSONArray l = (JSONArray) obj.get("locais");

        for (Object local : l) {
            JSONObject json = (JSONObject) local;
            Long id = (Long) json.get("id");
            JSONObject coord = (JSONObject) json.get("coordinates");
            Long latitude = (Long) coord.get("latitude");
            Long longitude = (Long) coord.get("longitude");
            JSONArray gs = (JSONArray) json.get("gameSettings");
            int id1 = id.intValue();
            double lat = latitude.doubleValue();
            double lon = longitude.doubleValue();
            if (((String) json.get("type")).equals("class ed_project.Game.Portal")) {
                for (Object gset : gs) {
                    JSONObject gameset = (JSONObject) gset;
                    Long energy = (Long) gameset.get("energy");
                    String name = (String) gameset.get("name");
                    Long energiaMaxima = (Long) gameset.get("maxEnergy");
                    JSONObject os = (JSONObject) gameset.get("ownership");
                    String ownership = (String) os.get("player");
                    DoubleNode<Player> playerTemp = gj.getPlayers().getHead();

                    boolean found = false;

                    while (playerTemp != null && found == false) {
                        if (playerTemp.getData().getName().equals(ownership)) {
                            found = true;
                        } else {
                            playerTemp = playerTemp.getNext();
                        }
                    }

                    Portal p1 = new Portal(id1, lat, lon, null, name, energiaMaxima, null);
                    double en = energy.doubleValue();
                    double me = energiaMaxima.doubleValue();
                    p1.setEnergy(en);
                    p1.setEnergiaMaxima(me);

                    if (found == true) {
                        p1.setOwnership(playerTemp.getData());
                        p1.setTeam(playerTemp.getData().getTeam());
                    }

                    gl.getLocais().addToRear(p1);

                }
            } else if (((String) json.get("type")).equals("class ed_project.Game.Connector")) {
                for (Object gset : gs) {
                    JSONObject gameset = (JSONObject) gset;
                    Long energy = (Long) gameset.get("energy");
                    Long cooldown = (Long) gameset.get("cooldown");
                    int cd = cooldown.intValue();
                    Connector c1 = new Connector(id1, lat, lon, cd);
                    double en = energy.doubleValue();
                    c1.setEnergy(en);
                    gl.getLocais().addToRear(c1);
                }
            }
        }
    }

    public void importRoutesFromJSON(String filename, GestaoLocais gl)
            throws FileNotFoundException, IOException, InvalidRoute, org.json.simple.parser.ParseException {

        JSONParser jsonParser = new JSONParser();
        JSONObject obj = (JSONObject) jsonParser.parse(new FileReader(filename));
        JSONArray r = (JSONArray) obj.get("Routes");

        for (Object route : r) {
            JSONObject json = (JSONObject) route;
            Long from = (Long) json.get("from");
            Long to = (Long) json.get("to");
            DoubleNode<Local> localTemp = gl.getLocais().getHead();
            boolean found = false;

            while (localTemp != null && found == false) {
                if (localTemp.getData().getId() == from) {
                    found = true;
                } else {
                    localTemp = localTemp.getNext();
                }
            }

            DoubleNode<Local> localTemp1 = gl.getLocais().getHead();
            boolean found1 = false;

            while (localTemp1 != null && found1 == false) {
                if (localTemp1.getData().getId() == to) {
                    found1 = true;
                } else {
                    localTemp1 = localTemp1.getNext();
                }
            }

            if (found && found1) {
                gl.adicionarRotasUmSentido(localTemp.getData(), localTemp1.getData());
            }
        }
    }
}