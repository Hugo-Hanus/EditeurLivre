package org.helmo.gbeditor.infrastructure.storage.storage.exception;

import java.sql.SQLException;

/**
 * Class UnableToRollbackException using when it can't roll back
 */
public class UnableToRollbackException  extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     *Cronstructor UnableToRollbackException
     * @param ex SqlException
     */
    public UnableToRollbackException(SQLException ex) {
        super(ex);
    }
}
