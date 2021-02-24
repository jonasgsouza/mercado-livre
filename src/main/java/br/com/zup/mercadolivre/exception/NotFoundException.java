package br.com.zup.mercadolivre.exception;

public class NotFoundException extends RuntimeException{

    private Object id;

    public NotFoundException(Object id) {
        super("Resource not found with id " + id);
    }
}
