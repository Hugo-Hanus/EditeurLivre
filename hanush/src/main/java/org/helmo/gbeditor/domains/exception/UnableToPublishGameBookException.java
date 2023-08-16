package org.helmo.gbeditor.domains.exception;

/**
 * Class UnableToPublishGameBookException : Exeption if a GameBook can't be published in the Program
 */
public class UnableToPublishGameBookException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    /**
     * Constructor of UnableToPublishGameBookException using a message
     * @param message String personalized  message of the error
     */
    public UnableToPublishGameBookException(String message){
        super(message);
    }
}
