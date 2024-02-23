package ed_project.Interfaces;

import ed_project.Exceptions.EmptyCollectionException;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public interface NetworkADT<T> extends GraphADT<T>{

    public void addEdge(T vertex1, T vertex2, double weight);

    public double shortestPathWeight(T vertex1, T vertex2) throws EmptyCollectionException;
}
