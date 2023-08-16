package org.helmo.gbeditor.infrastructure.storage.storage;

import org.helmo.gbeditor.domains.*;
import org.helmo.gbeditor.infrastructure.storage.dto.Choices;
import org.helmo.gbeditor.infrastructure.storage.dto.Library;
import org.helmo.gbeditor.infrastructure.storage.dto.Pages;
import org.helmo.gbeditor.infrastructure.storage.dto.Users;
import org.helmo.gbeditor.infrastructure.storage.storage.exception.*;
import org.helmo.gbeditor.storage.ILibraryStorage;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * LibraryStorage do the request to INSERT,UPDATE,DELETE Element in the DataBase
 */
public class LibraryStorage implements AutoCloseable, ILibraryStorage {


    private Connection connection;
    private Users users;
    private Library library;
    private Pages pages;
    private Choices choices;

    /**
     * Constructor of the LibraryStorage
     *
     * @param con     Connection using ConnectionData
     * @param users   Hashmap using |ID,User|
     * @param library Hashmap using |ID,GameBook|
     * @param pages   HashMap using |ID,Pages|
     * @param choices HashMap using |ID,CHOICES|
     */
    public LibraryStorage(Connection con, Users users, Library library, Pages pages, Choices choices) {
        this.connection = con;
        this.users = users;
        this.library = library;
        this.pages = pages;
        this.choices = choices;
    }


    @Override
    public void addUser(User user) {
        Transaction.from(connection)
                .commit((con) -> addingUser(user))
                .onRollback(ex -> {
                    throw new UnableToSetupException(ex);
                })
                .execute();
    }

    /**
     * Add the user in the DataBase
     *
     * @param user Object User
     * @return Boolean true if the user is added else false
     */
    private boolean addingUser(User user) {
        if (user != null) {
            try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO utilisateur(firstname,lastname) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, user.getFirstname());
                stmt.setString(2, user.getName());
                stmt.executeUpdate();
                try (ResultSet generatedKey = stmt.getGeneratedKeys()) {
                    if (generatedKey.next()) {
                        users.addUser(generatedKey.getInt(1), user);
                        return true;
                    }
                }

                return false;

            } catch (SQLException e) {
                throw new UnableToInsertInDb(e);
            }
        }
        return false;
    }

    @Override
    public void loadUsers() {

    }

    @Override
    public void addGamebook(GameBook gamebook, User user) {
        if (user.addGameBook(gamebook.getCover().getIsbn(), gamebook)) {
            Transaction.from(connection)
                    .commit((con) -> addingGameBook(gamebook, user))
                    .onRollback(ex -> {
                        throw new UnableToSetupException(ex);
                    })
                    .execute();
        } else {
            throw new UnableToInsertInDb("Impossible ajouter le livre dans la base de donnée");
        }
    }

    /**
     * Add GameBook in the DataBase
     *
     * @param gamebook Object GameBook
     * @param user  Object User
     * @return true if added else false
     */
    private boolean addingGameBook(GameBook gamebook, User user) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO gamebook(title,isbn,resume,is_publish,is_reedit,id_user) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, gamebook.getCover().getTitle());
            stmt.setString(2, gamebook.getCover().getIsbn());
            stmt.setString(3, gamebook.getCover().getResume());
            stmt.setBoolean(4, gamebook.isPublish());
            stmt.setBoolean(5, gamebook.isEdited());
            stmt.setInt(6, users.getId(user));
            stmt.executeUpdate();
            try (ResultSet generatedKey = stmt.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    user.addGameBook(gamebook.getCover().getIsbn(), gamebook);
                    library.addGamebook(generatedKey.getInt(1), gamebook);
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new UnableToInsertInDb(e);
        }
    }


    @Override
    public void loadGameBooks(User user) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT title,isbn,resume,is_publish,id_gamebook,is_reedit FROM gamebook WHERE id_user=?")) {
            stmt.setInt(1, users.getId(user));
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Cover cover = new Cover(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                    GameBook gamebook = new GameBook(cover, resultSet.getBoolean(4), resultSet.getBoolean(6), new HashMap<>());
                    if (user.addGameBook(resultSet.getString(2), gamebook)) {
                        library.addGamebook(resultSet.getInt(5), gamebook);
                    }
                }
            }
        } catch (SQLException e) {
            throw new UnableToSelectInDb(e);
        }
    }

    @Override
    public boolean addPage(Page page, int gameBook) {
        if (page != null) {
            try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO page(text,numero,id_gamebook) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, page.getTextPage());
                stmt.setInt(2, page.getNumero());
                stmt.setInt(3, gameBook);
                stmt.executeUpdate();
                try (ResultSet generatedKey = stmt.getGeneratedKeys()) {
                    if (generatedKey.next()) {
                        pages.addPages(generatedKey.getInt(1), new Page(page.getTextPage(), page.getNumero(), new ArrayList<>()));
                        return true;
                    }
                }
                return false;
            } catch (SQLException e) {
                throw new UnableToInsertInDb(e);
            }
        }
        return false;
    }

    @Override
    public void loadPages(GameBook gamebook) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT id_page,text,numero FROM page WHERE id_gamebook=?")) {
            stmt.setInt(1, library.getId(gamebook));
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Page page = new Page(resultSet.getString(2), resultSet.getInt(3), new ArrayList<>());
                    gamebook.addPage(page);
                    pages.addPages(resultSet.getInt(1), page);
                }
            }
        } catch (SQLException e) {
            throw new UnableToSelectInDb(e);
        }
    }


    @Override
    public void loadChoice(Page page) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT c.id_page,c.texteChoix,p.numero,c.id_choix from page p JOIN choix c on c.id_page_1=p.id_page WHERE p.id_page=c.id_page_1 and c.id_page=?")) {
            stmt.setInt(1, pages.getId(page));
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Choice choice = new Choice(page.getNumero(), resultSet.getInt(3), resultSet.getString(2));
                    page.addChoice(choice);
                    choices.addChoice(resultSet.getInt(4), choice);
                }
            }
        } catch (SQLException e) {
            throw new UnableToSelectInDb(e);
        }
    }

    @Override
    public boolean addChoice(Choice choice, int fromPageId, int toPageId) {
        if (choice != null) {
            try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO choix(id_page,texteChoix,id_page_1) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, fromPageId);
                stmt.setString(2, choice.getText());
                stmt.setInt(3, toPageId);
                stmt.executeUpdate();
                try (ResultSet generatedKey = stmt.getGeneratedKeys()) {
                    if (generatedKey.next()) {
                        pages.get(fromPageId).addChoice(choice);
                        choices.addChoice(generatedKey.getInt(1), choice);
                        return true;
                    }
                }
                return false;
            } catch (SQLException e) {
                throw new UnableToInsertInDb(e);
            }
        }
        return false;
    }

    /**
     * Add All Page of a GameBook in the database
     * @param newGameBook Object GameBook
     */
    public void addAllPageGameBook(GameBook newGameBook) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO page(text,numero,id_gamebook) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            for (Page page : newGameBook.getMapPage().values()) {
                stmt.setString(1, page.getTextPage());
                stmt.setInt(2, page.getNumero());
                stmt.setInt(3, library.getId(newGameBook));
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            throw new UnableToInsertInDb(e);
        }
    }

    /**
     * Add All Choices present in a GameBook
     * @param newGameBook Object GameBook
     */
    public void addAllChoiceFromPage(GameBook newGameBook) {
        int idBook = library.getId(newGameBook);
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO choix (id_page, texteChoix, id_page_1) VALUES ((SELECT DISTINCT p.id_page FROM page p WHERE p.numero = ? AND p.id_gamebook = ?),?,(SELECT DISTINCT p2.id_page FROM page p2 WHERE p2.numero = ? AND p2.id_gamebook = ?));", Statement.RETURN_GENERATED_KEYS)) {
            for (Choice choice : newGameBook.getAllChoiceofPages()) {
                stmt.setInt(1, choice.getFromPage());
                stmt.setInt(2, idBook);
                stmt.setString(3, choice.getText());
                stmt.setInt(4, choice.getToPage());
                stmt.setInt(5, library.getId(newGameBook));
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            throw new UnableToInsertInDb(e);
        }
    }

    @Override
    public boolean checkIfUserExistOrCreate(User user) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT id_user,firstname,lastname FROM utilisateur WHERE firstname=? AND lastname=?;")) {
            stmt.setString(1, user.getFirstname());
            stmt.setString(2, user.getName());
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    users.addUser(resultSet.getInt(1), user);
                } else {
                    addUser(user);
                }
                return true;
            } catch (UnableToSetupException ex) {
                throw new UnableToSetupException(ex);
            }
        } catch (SQLException e) {
            throw new UnableToSelectInDb(e);
        }

    }

    @Override
    public boolean isClosed() {
        try {
            return connection.isClosed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateGameBookInfo(GameBook choose, String title, String isbn, String resume) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE gamebook SET title=?,isbn=?,resume=? WHERE isbn=?")) {
            stmt.setString(1, title);
            stmt.setString(2, isbn);
            stmt.setString(3, resume);
            stmt.setString(4, choose.getCover().getIsbn());
            if (stmt.execute()) {
                choose.setCover(new Cover(title, isbn, resume));
            }
        } catch (SQLException ex) {
            throw new UnableToUpdateInformation(ex);
        }
    }

    /**
     * Publish GameBook int the DataBase
     *
     * @param gb Object GameBook
     */
    @Override
    public void publishGameBook(GameBook gb) {
        int id = library.getId(gb);
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE gamebook SET is_publish = 1 ,is_reedit = 0 WHERE id_gamebook=?")) {
            stmt.setInt(1, id);
            if (stmt.execute()) {
                gb.setPublish(true);
                library.get(id).setPublish(true);
            }
        } catch (SQLException e) {
            throw new UnableToSelectInDb(e);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new DeconnectionFailedException(ex);
        }
    }

    /**
     * Add Page if the number already Exist
     *
     * @param page       Objetc Page
     * @param idGameBook Int id of the gameBook
     */
    @Override
    public void addExistPage(Page page, int idGameBook) {
        int generated = 0;
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO page(text,numero,id_gamebook) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, page.getTextPage());
            stmt.setInt(2, page.getNumero());
            stmt.setInt(3, idGameBook);
            stmt.executeUpdate();
            try (ResultSet generatedKey = stmt.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    generated = generatedKey.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new UnableToInsertInDb(e);
        }
        updateOtherPage(page, idGameBook, generated);
    }

    private void updateOtherPage(Page page, int idGameBook, int generated) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE page SET numero = numero + 1 WHERE numero >= ? AND (id_gamebook = ? AND id_page <> ?);", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, page.getNumero());
            stmt.setInt(2, idGameBook);
            stmt.setInt(3, generated);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new UnableToInsertInDb(e);
        }
    }


    /**
     * Delete a Page using id of the page
     *
     * @param idPage Int number of the page
     */
    @Override
    public void deleteChoicePage(int idPage) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE c FROM choix c WHERE c.id_page = ? OR c.id_page_1 = ?;")) {
            stmt.setInt(1, idPage);
            stmt.setInt(2, idPage);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new UnableDeleteException(e);
        }

    }

    @Override
    public void deletePage(int idPage) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE p FROM page p WHERE p.id_page = ?;")) {
            stmt.setInt(1, idPage);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new UnableDeleteException(e);
        }
    }

    @Override
    public void updateDeletedPage(int idPage, int idGameBook, int number) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE page SET numero = numero - 1 WHERE numero >= ? AND (id_gamebook = ? AND id_page <> ?);")) {
            stmt.setInt(1, number);
            stmt.setInt(2, idGameBook);
            stmt.setInt(3, idPage);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new UnableDeleteException(e);
        }
    }

    @Override
    public void deleteChoice(int idChoice) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE c FROM choix c WHERE c.id_choix = ?;")) {
            stmt.setInt(1, idChoice);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new UnableDeleteException(e);
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Users getUsers() {
        return users;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public Library getLibrary() {
        return library;
    }

    public void setPages(Pages pages) {
        this.pages = pages;
    }

    public Pages getPages() {
        return pages;
    }

    public void setChoices(Choices choices) {
        this.choices = choices;
    }

    public Choices getChoices() {
        return choices;
    }


}
