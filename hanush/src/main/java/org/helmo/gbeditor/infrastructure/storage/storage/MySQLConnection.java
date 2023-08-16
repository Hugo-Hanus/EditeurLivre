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
        return "in19b1143";
    }

    @Override
    public String getPassword() {
        return "5559";
    }

    @Override
    public String getDBPath() {
        return"jdbc:mysql://192.168.128.13:3306/in19b1143?userSSL=false&serverTimezone=UTC";
    }
}
