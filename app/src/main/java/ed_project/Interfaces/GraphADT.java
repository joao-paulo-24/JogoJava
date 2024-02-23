package ed_project.Interfaces;

import java.util.Iterator;

import ed_project.Exceptions.EmptyCollectionException;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public interface GraphADT<T> {

    public void addVertex(T vertex);

    public void removeVertex(T vertex);

    public void addEdge(T vertex1, T vertex2);

    public void removeEdge(T vertex1, T vertex2);

    public Iterator iteratorBFS(T startVertex) throws EmptyCollectionException;

    public Iterator iteratorDFS(T startVertex);

    public Iterator iteratorShortestPath(T startVertex, T targetVertex) throws EmptyCollectionException;

    public boolean isEmpty();

    public boolean isConnected() throws EmptyCollectionException;

    public int size();

    public String toString();
}
