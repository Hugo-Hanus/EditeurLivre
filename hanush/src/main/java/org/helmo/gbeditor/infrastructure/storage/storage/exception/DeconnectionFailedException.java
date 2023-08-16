package org.helmo.gbeditor.infrastructure.storage.storage.exception;

import java.sql.SQLException;

/**
 * DeconnectionFailedException Exception if the deconnection to the Database can't be done extends RunTimeException
 */
public class DeconnectionFailedException extends RuntimeException{
    private static final long serialVersionUID=1L;

    /**
     * Contructor of the Exception DeconnectionFailedException
     * @param ex
     */
    public DeconnectionFailedException(SQLException ex){
        super(ex);
    }
}
