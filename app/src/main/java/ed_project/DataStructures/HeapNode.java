package ed_project.DataStructures;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class HeapNode<T> extends BinaryTreeNode<T> {
    protected HeapNode<T> parent;

    /**
     * Creates a new heap node with the specified data.
     * 
     * @param obj the data to be contained within
     *            the new heap nodes
     */
    HeapNode(T obj) {
        super(obj);
        parent = null;
    }
}
