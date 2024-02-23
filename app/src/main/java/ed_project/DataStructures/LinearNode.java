package ed_project.DataStructures;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class LinearNode<T> {
    private LinearNode<T> next;
    private T element;

    public LinearNode() {
        next = null;
        element = null;
    }

    public LinearNode(T elem) {
        next = null;
        element = elem;
    }

    public T getElement() {
        return this.element;
    }

    public void setElement(T elem) {
        this.element = elem;
    }

    public LinearNode<T> getNext() {
        return this.next;
    }

    public void setNext(LinearNode<T> next) {
        this.next = next;
    }

    public boolean compareTo(LinearNode next) {
        return this.element.equals(next.element);
    }
}
