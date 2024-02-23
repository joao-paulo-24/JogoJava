package ed_project.Game;

import java.time.LocalDateTime;

import ed_project.Enumerations.interactionType;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class Interacao {
    private Player player;
    private interactionType interactionType;
    private LocalDateTime time;

    public Interacao(Player player, interactionType interactionType, LocalDateTime time) {
        this.player = player;
        this.interactionType = interactionType;
        this.time = time;
    }


    public Player getPlayer() {
        return player;
    }


    public void setPlayer(Player player) {
        this.player = player;
    }


    public interactionType getinteractionType() {
        return interactionType;
    }


    public void setinteractionType(interactionType interactionType) {
        this.interactionType = interactionType;
    }


    public LocalDateTime getTime() {
        return time;
    }


    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    
}
