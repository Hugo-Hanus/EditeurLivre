package org.helmo.gbeditor.infrastructure.storage.storage;
import org.helmo.gbeditor.infrastructure.storage.storage.exception.UnableToRollbackException;

import java.sql.*;

/**
 *
 */
public class Transaction {

    private final Connection con;
    private static ExceptionHandle rollbackAction;
    private static ActionThrowingException commitAction;

    /**
     *
     * @param con
     * @return
     */
    public static Transaction from(Connection con) {
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            throw new TransactionNotSupportedException(ex);
        }
        return new Transaction(con);
    }

    private Transaction(Connection con) {
        this.con = con;
    }

    /**
     *
     * @param sequence
     * @return
     */
    public Transaction commit(ActionThrowingException sequence) {
        this.commitAction = sequence;
        return this;
    }

    /**
     *
     * @param sequence
     * @return
     */
    public Transaction onRollback(ExceptionHandle sequence) {
        this.rollbackAction = sequence;
        return this;
    }

    /**
     *
     * @throws UnableToRollbackException
     */
    public void execute() throws UnableToRollbackException {
        try {
            commitAction.execute(con);
            con.commit();
        } catch (Exception ex) {
            try {
                con.rollback();
                rollbackAction.handle(ex);
            } catch (SQLException e) {
                throw new UnableToRollbackException(e);
            }
        } finally {
            try {
                con.setAutoCommit(true); //Active la gestion automatique des transactions
            } catch(SQLException ex) {
                throw new TransactionNotSupportedException(ex);
            }
        }
    }

    public Connection getCon() {
        return con;
    }

}

/**
 *
 */
class TransactionNotSupportedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param ex
     */
    public TransactionNotSupportedException(SQLException ex) {
        super("Transaction are not supported by this DBMS or this driver", ex);
    }
}

/**
 *
 */
@FunctionalInterface
interface ActionThrowingException {
    /**
     *
     * @param con
     * @throws Exception
     */
    void execute(Connection con) throws Exception;
}

/**
 *
 */
@FunctionalInterface
interface ExceptionHandle {
    /**
     *
     * @param ex
     */
    void handle(Exception ex);
}
