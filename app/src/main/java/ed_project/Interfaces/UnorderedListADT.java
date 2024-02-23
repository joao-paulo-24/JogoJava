package ed_project.Interfaces;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public interface UnorderedListADT<T> extends ListADT<T>{
    public void addToFront(T element);
    public void addToRear (T element);
    public void addAfter (T element, T target);
}