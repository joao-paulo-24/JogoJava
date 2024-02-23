package ed_project.Interfaces;

import java.util.Iterator;

import ed_project.Exceptions.EmptyCollectionException;
import ed_project.Exceptions.NoSuchElementException;
import ed_project.Exceptions.QueueArrayException;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public interface BinaryTreeADT<T> {
    /**
     * Returns a reference to the root element
     *
     * @return a reference to the root
     */
    T getRoot ();

    /**
     * Returns true if this binary tree is empty and false otherwise.
     *
     * @return true if this binary tree is empty
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in this binary tree.
     *
     * @return the integer number of elements in this tree
     */
    int size();

    /**
     * Returns true if the binary tree contains an element that
     * matches the specified element and false otherwise.
     *
     * @param targetElement the element being sought in the tree
     * @return true if the tree contains the target element
     */
    boolean contains (T targetElement);

    /**
     * Returns a reference to the specified element if it is found in
     * this binary tree. Throws an exception if the specified element
     * is not found.
     *
     * @param targetElement the element being sought in the tree
     * @return a reference to the specified element
     * @throws NoSuchElementException
     */
    T find (T targetElement) throws NoSuchElementException;

    /**
     * Returns the string representation of the binary tree.
     *
     * @return a string representation of the binary tree
     */
    String toString();

    /**
     * Performs an inorder traversal on this binary tree by calling an
     * overloaded, recursive inorder method that starts with the root.
     *
     * @return an iterator over the elements of this binary tree
     */
    Iterator<T> iteratorInOrder();

    /**
     * Performs a preorder traversal on this binary tree by calling an
     * overloaded, recursive preorder method that starts
     * with the root.
     *
     * @return an iterator over the elements of this binary tree
     */
    Iterator<T> iteratorPreOrder();

    /**
     * Performs a postorder traversal on this binary tree by
     * calling an overloaded, recursive postorder
     * method that starts with the root.
     *
     * @return an iterator over the elements of this binary tree
     */
    Iterator<T> iteratorPostOrder();

    /**
     * Performs a levelorder traversal on the binary tree,
     * using a queue.
     *
     * @return an iterator over the elements of this binary tree
     * @throws EmptyCollectionException
     * @throws QueueArrayException
     */
    Iterator<T> iteratorLevelOrder() throws QueueArrayException, EmptyCollectionException;
}
