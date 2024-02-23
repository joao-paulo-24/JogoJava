package ed_project.DataStructures;

import ed_project.Interfaces.UnorderedListADT;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class DoubleLinkedUnorderedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T> {

    public DoubleLinkedUnorderedList() {
    }

    @Override
    public void addToFront(T element) {
        DoubleNode<T> node = new DoubleNode(element);
        if (super.count == 0) {
            super.head = super.tail = node;
            super.count++;
            super.modCount++;
        } else {
            node.setNext(super.head);
            super.head.setPrevious(node);
            node.setPrevious(null);
            super.head = node;
            super.count++;
            super.modCount++;
        }
    }

    @Override
    public void addToRear(T element) {
        DoubleNode<T> node = new DoubleNode(element);
        if (super.count == 0) {
            super.head = super.tail = node;
            super.count++;
            super.modCount++;
        } else {
            node.setNext(null);
            super.tail.setNext(node);
            node.setPrevious(super.tail);
            super.tail = node;
            super.count++;
            super.modCount++;
        }
    }

    @Override
    public void addAfter(T element, T target) {
        DoubleNode<T> temp = super.head;
        if (super.count == 0) {
            throw new NullPointerException("Lista vazia");
        } else {
            while (temp != tail && temp.getData() != target) {
                temp = temp.getNext();
            }
            DoubleNode<T> node = new DoubleNode(element);
            node.setNext(temp.getNext());
            node.setPrevious(temp);
            temp.getNext().setPrevious(node);
            temp.setNext(node);
        }
    }

}
