package ed_project.Game;

import ed_project.DataStructures.DoubleLinkedUnorderedList;
import ed_project.DataStructures.DoubleNode;
import ed_project.Exceptions.*;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class Connector extends Local{
    private int cooldown;
    private DoubleLinkedUnorderedList<Interacao> interacoes;


    public Connector(double energy, double latitude, double longitude, int cooldown) {
        super(energy, latitude, longitude);
        this.cooldown = cooldown;
        this.interacoes = new DoubleLinkedUnorderedList<>();
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) throws NotAcceptedValue {
        if (cooldown < 0) {
            throw new NotAcceptedValue("O cooldown não pode ser negativo");
        } else {
            this.cooldown = cooldown;
        }
    }
    
    public DoubleLinkedUnorderedList<Interacao> getInteracoes() {
        return interacoes;
    }

    public void setInteracoes(DoubleLinkedUnorderedList<Interacao> interacoes) {
        this.interacoes = interacoes;
    }

    public void addInteracoes(Interacao interacao) {
        interacoes.addToFront(interacao);
    }

    public void removeInteraction(int id) throws EmptyCollectionException {
        DoubleNode<Interacao> current_interaction = new DoubleNode<>();
        current_interaction = interacoes.getHead();
        boolean found = false;

        while (current_interaction != null && !found) {
            if (current_interaction.getData().getPlayer().getId() == id) {
                found = true;
                interacoes.remove(current_interaction.getData());
            } else {
                current_interaction = current_interaction.getNext();
            }
        }
    }

}

