package ed_project.Exceptions;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class InvalidRoute extends Exception{
    /**
     * Cria uma instância da exceção sem mensagem
     */
    public InvalidRoute() {
    }

    /**
     * Cria uma instância da exceção com uma mensagem
     * 
     * @param msg
     */
    public InvalidRoute(String msg) {
        super(msg);
    }    
}
