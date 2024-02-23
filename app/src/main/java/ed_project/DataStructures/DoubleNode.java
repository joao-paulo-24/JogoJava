package ed_project.DataStructures;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class DoubleNode<T> {
    private T data;
    private DoubleNode<T> next, previous;

    public DoubleNode() {
        this.data = null;
        this.next = null;
        this.previous = null;
    }

    public DoubleNode(T data) {
        this.data = data;
        this.next = null;
        this.previous = null;
    }

    public DoubleNode<T> getPrevious() {
        return previous;
    }

    public void setPrevious(DoubleNode<T> previous) {
        this.previous = previous;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setNext(DoubleNode next) {
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public DoubleNode getNext() {
        return next;
    }
}
