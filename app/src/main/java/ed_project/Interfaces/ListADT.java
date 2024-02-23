package ed_project.Interfaces;

import java.util.Iterator;

import ed_project.Exceptions.EmptyCollectionException;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public interface ListADT<T> {
    /*
     * Remove o primeiro nó da lista
     */
    public T removeFirst() throws EmptyCollectionException;

    /**
     * Remove o último nó da lista
     * @return o elemento do referente nó
     * @throws EmptyCollectionException Caso a lista esteja vazia
     */
    public T removeLast() throws EmptyCollectionException;

    /**
     * Remove um nó passado como argumento
     * @param element elemento do nó que se quer remover
     * @return elemento do referente nó
     * @throws EmptyCollectionException Caso a lista esteja vazia
     */
    public T remove(T element) throws EmptyCollectionException;

    /**
     * Obtem-se a informação do primeiro nó da lista
     * @return o elemento do primeiro nó da lista
     * @throws EmptyCollectionException Caso a lista esteja vazia
     */
    public T first() throws EmptyCollectionException;

    /**
     * Obtem-se a informação do último nó da lista
     * @return o elemento do último nó da lista
     * @throws EmptyCollectionException Caso a lista esteja vazia
     */
    public T last() throws EmptyCollectionException;

    /**
     * Verifica se a lista contém o elemento passado como argumento
     * @param target elemento a confirmar se existe na lista
     * @return true caso o elemento exista na lista, false caso contrário
     * @throws EmptyCollectionException Caso a lista esteja vazia
     */
    public boolean contains(T target) throws EmptyCollectionException;

    /**
     * Verifica se a lista está vazia
     * @return true se a lista estiver vazia, false caso contrário
     */
    public boolean isEmpty();

    /**
     * Obtém o tamanho da lista
     * @return o tamanho da lista
     */
    public int size();

    /**
     * Iterador que vai operar sobre a lista
     * @return o iterador que vai ser usado
     */
    public Iterator<T> iterator();

    /**
     * Método toString da linkedlist
     */
    @Override
    public String toString();
}
