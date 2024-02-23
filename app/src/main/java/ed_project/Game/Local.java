package ed_project.Game;

import ed_project.DataStructures.DoubleLinkedUnorderedList;
import ed_project.Exceptions.EmptyCollectionException;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
abstract public class Local {
    private static int nextId;
    private int id;
    private double energy;
    private double latitude;
    private double longitude;
    private DoubleLinkedUnorderedList<Local> conexoes;

    public Local(double energy, double latitude, double longitude) {
        this.id = nextId++;
        this.energy = energy;
        this.latitude = latitude;
        this.longitude = longitude;
        this.conexoes = new DoubleLinkedUnorderedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        if (energy < 0) {
            System.out.println("A energia não pode ser negativa.");
        } else {
            this.energy = energy;
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public DoubleLinkedUnorderedList<Local> getConexoes() {
        return conexoes;
    }

    public void setConexoes(DoubleLinkedUnorderedList<Local> conexoes) {
        this.conexoes = conexoes;
    }

    public void addConections(Local local) {
        conexoes.addToRear(local);
    }

    public void removeConections(Local local) throws EmptyCollectionException {
        conexoes.remove(local);
    }

}
