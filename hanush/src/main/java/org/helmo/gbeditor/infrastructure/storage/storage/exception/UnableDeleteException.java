package org.helmo.gbeditor.infrastructure.storage.storage.exception;

import java.sql.SQLException;

/**
 * Exception UnableDeleteException use when it can delete tuple
 */
public class UnableDeleteException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    /**
     * Cronstructor UnableDeleteException SQLException
     * @param e SQLException
     */
    public UnableDeleteException(SQLException e) {super(e);}

    /**
     * Cronstructor UnableDeleteException SQLException
     * @param message String message
     */
    public UnableDeleteException (String message){super(message);}
}
