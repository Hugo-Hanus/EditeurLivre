package org.helmo.gbeditor.infrastructure.storage.storage.exception;

import java.sql.SQLException;

/**
 * Exception UnableToInsertInDb using when it can't insert tuple in the DataBase
 */
public class UnableToInsertInDb extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     *Cronstructor UnableToInsertInDb
     * @param e SQLException
     */
    public UnableToInsertInDb(SQLException e) {super(e);}

    /**
     *Cronstructor UnableToInsertInDb
     * @param message String message
     */
    public UnableToInsertInDb (String message){super(message);}
}
