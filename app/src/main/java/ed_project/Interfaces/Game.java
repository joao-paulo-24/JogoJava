package ed_project.Interfaces;

import java.io.IOException;

import ed_project.Exceptions.*;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public interface Game {
    public void Move() throws EmptyCollectionException, NoSuchElementException, NumberFormatException, IOException;

    public void chargePortal(double energy) throws NoSuchElementException;

    public void attackPortal() throws NoSuchElementException, EmptyCollectionException, NoSuchTeamExist, NotAcceptedValue;

    public void refillEnergy() throws NoSuchElementException;
}
