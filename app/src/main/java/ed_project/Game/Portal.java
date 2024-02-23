package ed_project.Game;

import ed_project.Enumerations.Teams;
import ed_project.Exceptions.NotAcceptedValue;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class Portal extends Local{
    private Player ownership;
    private String name;
    private double energiaMaxima;
    private Teams team;
    
    public Portal(int energy, double latitude, double longitude, Player ownership, String name, double energiaMaxima, Teams team) {
        super(energy, latitude, longitude);
        this.ownership = ownership;
        this.name = name;
        this.energiaMaxima = energiaMaxima;
        this.team = team;
    }

    
    public Player getPlayer() {
        return ownership;
    }


    public void setPlayer(Player player) {
        this.ownership = player;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Player getOwnership() {
        return ownership;
    }


    public void setOwnership(Player ownership) {
        this.ownership = ownership;
    }


    public double getEnergiaMaxima() {
        return energiaMaxima;
    }


    public void setEnergiaMaxima(double energiaMaxima) throws NotAcceptedValue {
        if (energiaMaxima < 0) {
            throw new NotAcceptedValue("A energia máxima não pode ser negativa");
        } else {
            this.energiaMaxima = energiaMaxima;
        }
    }


    public Teams getTeam() {
        return team;
    }


    public void setTeam(Teams team) {
        this.team = team;
    }

    
}
