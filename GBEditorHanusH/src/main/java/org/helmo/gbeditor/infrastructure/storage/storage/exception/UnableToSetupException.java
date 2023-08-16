package org.helmo.gbeditor.infrastructure.storage.storage.exception;

/**
 * Exception UnableToSetupException using when the DataBase can't be setup
 */
public class UnableToSetupException extends  RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     *Cronstructor UnableToSetupException
     * @param ex Exception
     */
    public UnableToSetupException(Exception ex) {
        super(ex);
    }

}