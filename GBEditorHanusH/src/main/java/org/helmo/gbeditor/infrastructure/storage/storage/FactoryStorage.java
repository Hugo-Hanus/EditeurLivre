package org.helmo.gbeditor.infrastructure.storage.storage;
import org.helmo.gbeditor.infrastructure.storage.dto.Choices;
import org.helmo.gbeditor.infrastructure.storage.dto.Library;
import org.helmo.gbeditor.infrastructure.storage.dto.Pages;
import org.helmo.gbeditor.infrastructure.storage.dto.Users;
import org.helmo.gbeditor.storage.ConnectionData;
import org.helmo.gbeditor.infrastructure.storage.storage.exception.UnableToSetupException;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class SQLFactoryStorage
 * DP Factory: for Creation of a Connection to a DataBase
 */
public class FactoryStorage {
    private  final String db ;
    private final String username;
    private final String password;

    /**
     * Constructor of The SQLFactory using an Interface for Connection
     * @param connectionData Interface Connection Data
     */
    public FactoryStorage(ConnectionData connectionData) {
        this.db=connectionData.getDBPath();
        this.password=connectionData.getPassword();
        this.username= connectionData.getUsername();

        try{
            Class.forName(connectionData.getDriverName());
        }catch (ClassNotFoundException e){
            throw new UnableToSetupException(e);
        }
    }

    /**
     * Create a connection to X DataBase and create SQLLibraryStorage for using Request
     * @param users Collection of User
     * @param library Collection of GameBook
     * @param pages Collection of Page
     * @param choices Collection of Choices
     * @return A SQLLibrarryStorage Storage connected to X DataBase for use Request action
     */
    public LibraryStorage newStorage(Users users, Library library, Pages pages, Choices choices)  {
        try {
            return new LibraryStorage(DriverManager.getConnection(db,username,password),users,library,pages,choices);
        } catch (SQLException e) {
            throw new UnableToSetupException(e);
        }
    }


    public String getDb() {
        return db;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
