package ed_project.Game;

import ed_project.Enumerations.Teams;
import ed_project.Exceptions.NotAcceptedValue;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class Player {
    private String name;
    private static int nextId;
    private int id;
    private Teams team;
    private double current_energy;
    private double experiencePoints;
    private int level;
    private Local localAtual;
    private double expToNextLevel;
    private final int X = 6;
    private final int Y = 3;
    private int nPortais;

    /**
     * Método para criar uma instância de player com o nome e a team a que pertence
     * @param name Nome do jogador
     * @param team Equipa a que pertence
     */
    public Player(String name, Teams team) {
        this.name = name;
        this.id = nextId++;
        this.current_energy = 0;
        this.level = 1;
        this.experiencePoints = 0;
        this.team = team;
        this.localAtual = null;
        this.expToNextLevel = 100;
        this.nPortais = 0;
    }
    
    /**
     * Método para criar uma instância de player sem dados
     */
    public Player() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCurrent_energy() {
        return current_energy;
    }

    public void setCurrent_energy(double current_energy) {
        this.current_energy = current_energy;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) throws NotAcceptedValue {
        if (level < 0) {
            throw new NotAcceptedValue("O nivel não pode ser negativo");
        } else {
            this.level = level;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getExperiencePoints() {
        return experiencePoints;
    }

    public void setExperiencePoints(double experiencePoints) throws NotAcceptedValue {
        if (experiencePoints < 0) {
            throw new NotAcceptedValue("Os pontos de experiência não podem ser negativos");
        } else {
            this.experiencePoints = experiencePoints;
        }
    }

    public Teams getTeam() {
        return team;
    }

    public void setTeam(Teams team) {
        this.team = team;
    }

    public Local getLocalAtual() {
        return localAtual;
    }

    public void setLocalAtual(Local localAtual) {
        this.localAtual = localAtual;
    }

    public double getExpToNextLevel() {
        return expToNextLevel;
    }

    public void setExpToNextLevel(double expToNextLevel) {
        this.expToNextLevel = expToNextLevel;
    }

    public int getnPortais() {
        return nPortais;
    }

    public void setnPortais(int nPortais) {
        this.nPortais = nPortais;
    }

    /**
     * Metodo usado para calcular e mudar para o proximo nivel ou não
     */
    public void changetoNextLevel() {

        while (this.experiencePoints > this.expToNextLevel) {
            int temp_level = this.level;
            temp_level++;
            this.level = temp_level;

            System.out.println("Parabens, subiu de nivel! Atualmente está no nivel " + this.level);

            double temp_exp = this.expToNextLevel;
            temp_exp += temp_exp * Math.pow(temp_level/X, Y);
            this.expToNextLevel = temp_exp;
        }

    }

    /**
     * Metodo usado para comparar o nivel
     * @param o Jogador a ser comparado
     * @return -1 caso o jogador da classe tenha um nivel maior
     *          1 caso o jogador da classe tenha um nivel menor
     *          0 caso os niveis sejam iguais
     */
    public int compareToLevel(Object o) {
        Player p = (Player) o;
        
        if(this.level > p.level){
            return -1;
        }
        
        if(this.level < p.level){
            return 1;
        }
        
        return 0;
    }
    
    /**
     * Metodo usado para comparar o numero de portais
     * @param o jogador a ser comparado
     * @return -1 caso o jogador da classe tenha um numero de portais maior
     *          1 caso o jogador da classe tenha um numero de portais menor
     *          0 caso os numeros de portais sejam iguais
     */
    public int compareToNPortals(Object o) {
        Player p = (Player) o;
        
        if(this.nPortais > p.nPortais){
            return -1;
        }
        
        if(this.nPortais < p.nPortais){
            return 1;
        }
        
        return 0;
    }
    
}
