package ed_project.Exceptions;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class NoSuchTeamExist extends Exception{
    /**
     * Cria uma instância da exceção sem mensagem
     */
    public NoSuchTeamExist() {
    }

    /**
     * Cria uma instância da exceção com uma mensagem
     * @param msg
     */
    public NoSuchTeamExist(String msg) {
        super(msg);
    }
}
