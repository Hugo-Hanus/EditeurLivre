package org.helmo.gbeditor.infrastructure.storage.storage.exception;

/**
 *  Exception UnableToConnectToDb use when the application can't connect to the DataBase
 */
public class UnableToConnectToDb extends RuntimeException{
    private static final long serialVersionUID=1L;

    /**
     *Constructor of UnableToConnectToDb
     * @param ex Exception
     */
    public UnableToConnectToDb(Exception ex){super((ex));}
}
