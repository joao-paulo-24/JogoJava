package ed_project.Interfaces;

import ed_project.Exceptions.EmptyCollectionException;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public interface HeapADT<T> extends BinaryTreeADT<T>{
    /**
     * Adds the specified object to this heap.
     *
     * @param obj the element to added to this head
     */
    void addElement(T obj);

    /**
     * Removes element with the lowest value from this heap.
     *
     * @return the element with the lowest value from this heap
     * @throws EmptyCollectionException
     */
    T removeMin() throws EmptyCollectionException, EmptyCollectionException;

    /**
     * Returns a reference to the element with the lowest value in
     * this heap.
     *
     * @return a reference to the element with the lowest value
     *         in this heap
     */
    T findMin();
}
