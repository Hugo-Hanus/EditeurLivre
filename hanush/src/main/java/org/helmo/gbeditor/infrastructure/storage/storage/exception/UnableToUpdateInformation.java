package org.helmo.gbeditor.infrastructure.storage.storage.exception;

import java.sql.SQLException;

/**
 * Exception UnableToUpdateInformation using when it can't update information
 */
public class UnableToUpdateInformation extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     *Cronstructor  UnableToUpdateInformation
     * @param e SqlException
     */
    public UnableToUpdateInformation(SQLException e) {super(e);}

    /**
     *Cronstructor UnableToUpdateInformation
     * @param message String message
     */

    public UnableToUpdateInformation(String message){super(message);}
}
