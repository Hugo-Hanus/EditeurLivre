package org.helmo.gbeditor.infrastructure.storage.storage;

import org.helmo.gbeditor.storage.ConnectionData;

/**
 * Class MYSQLConnection implements the Interface ConnectionDate
 * Can get the DriverName,Username,Password and the Path to a MYSQL DataBase
 */
public class MySQLConnection implements ConnectionData {
    @Override
    public String getDriverName() {
        return "com.mysql.jdbc.Driver";
    }

    @Override
    public String getUsername() {
        return "userDB";
    }

    @Override
    public String getPassword() {
        return "Password";
    }

    @Override
    public String getDBPath() {
        return"jdbc:mysql://ipUserServeur?userSSL=false&serverTimezone=UTC";
    }
}
