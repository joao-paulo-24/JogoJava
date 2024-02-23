package ed_project.Exceptions;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class NotEnoughObjects extends Exception{
    /**
     * Cria uma instância da exceção sem mensagem
     */
    public NotEnoughObjects() {
    }

    /**
     * Cria uma instância da exceção com uma mensagem
     * 
     * @param msg
     */
    public NotEnoughObjects(String msg) {
        super(msg);
    }    
}
