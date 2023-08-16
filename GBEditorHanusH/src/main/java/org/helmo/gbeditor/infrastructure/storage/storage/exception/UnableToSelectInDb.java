package org.helmo.gbeditor.infrastructure.storage.storage.exception;

import java.sql.SQLException;

/**
 * Exception UnableToSelectInDb using when it can't select tuple in the Database
 */
public class UnableToSelectInDb extends RuntimeException {
    private static final long serialVersionUID=1L;

    /**
     *Cronstructor of UnableToSelectInDb
     * @param e SQLException
     */
    public UnableToSelectInDb(SQLException e) {super(e);}
}
