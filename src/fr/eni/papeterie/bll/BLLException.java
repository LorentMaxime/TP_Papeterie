package fr.eni.papeterie.bll;

public class BLLException extends Exception{
    String message;

    public BLLException() {
    }

    public BLLException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
