package ed_project.Exceptions;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class NotAcceptedInteraction extends Exception{
    /**
     * Cria uma instância da exceção sem mensagem
     */
    public NotAcceptedInteraction() {
    }

    /**
     * Cria uma instância da exceção com uma mensagem
     * 
     * @param msg
     */
    public NotAcceptedInteraction(String msg) {
        super(msg);
    }    
}
