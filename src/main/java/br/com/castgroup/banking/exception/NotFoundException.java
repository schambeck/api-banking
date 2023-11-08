package br.com.castgroup.banking.exception;

public class NotFoundException extends BusinessException {
    public NotFoundException(String message) {
        super(message);
    }
}
