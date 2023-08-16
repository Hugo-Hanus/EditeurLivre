package org.helmo.gbeditor.storage;

import org.helmo.gbeditor.domains.Choice;
import org.helmo.gbeditor.domains.GameBook;
import org.helmo.gbeditor.domains.Page;
import org.helmo.gbeditor.domains.User;

/**
 * Interface ILibraryStorage using for operation to the DataBase
 */
public interface ILibraryStorage extends AutoCloseable {
    /**
     * Add user in DataBase
     *
     * @param user Object User
     */
    void addUser(User user);

    /**
     * Load all User
     */
    void loadUsers();

    /**
     * Add Gamebook to the DataBase
     *
     * @param gamebook Object gameBook
     * @param user     Object User
     */
    void addGamebook(GameBook gamebook, User user);

    /**
     * Load GameBook from user
     *
     * @param user Object User
     */
    void loadGameBooks(User user);

    /**
     * Add Page to a GameBook using id of the GameBook
     *
     * @param page       Object page
     * @param idGameBook Int id of the gameBook
     * @return
     */
    boolean addPage(Page page, int idGameBook);

    /**
     * Load all Page of the GameBook
     *
     * @param gamebook Object GameBook
     */
    void loadPages(GameBook gamebook);

    /**
     * Load all Choices
     *
     * @param page Object Page
     */
    void loadChoice(Page page);

    /**
     * Add Choice
     *
     * @param choice     Object Choice
     * @param fromPageId int id from the page of the choice
     * @param toPageId   int id the choice go to
     * @return boolean if success return true else return false
     */
    boolean addChoice(Choice choice, int fromPageId, int toPageId);

    /**
     * Check if the user exist
     *
     * @param user Object User
     * @return true if exist else false
     */

    boolean checkIfUserExistOrCreate(User user);

    /**
     * Check if the connection is Closed
     *
     * @return True if it is close else false
     */

    boolean isClosed();

    /**
     * Update the GameBook information
     *
     * @param choose Object GameBook
     * @param title  String title of the new GameBook
     * @param isbn   String isbn of the new GameBook
     * @param resume String resume of the new GameBook
     */

    void updateGameBookInfo(GameBook choose, String title, String isbn, String resume);

    /**
     * Publish a GameBook
     * @param gb Object Gamebook to be published
     */
    void publishGameBook(GameBook gb);

    /**
     * Add a Page to a gamebook which the number his already present
     * @param page Object Page of the GameBook
     * @param idGameBook Integer id of the gameBook
     */
    void addExistPage(Page page, int idGameBook);

    /**
     * Delete All Choices in the page or if the choice is pointing this page
     * @param idPage Integer id of the page
     */
    void deleteChoicePage(int idPage);

    /**
     * Delete a page of a gamebook
     * @param idPage Integer id of the page to be deleted
     */
    void deletePage(int idPage);

    /**
     * Update the other page after the page is deleted
     * @param idPage Integer id of the page to be deleted
     * @param idGameBook Integer id of the gameBook of the page
     * @param number Integer number of the page in the gamebook
     */
    void updateDeletedPage(int idPage, int idGameBook, int number);

    /**
     * Delete the choice using hid id
     * @param idChoice int ID of the choice
     */
    void deleteChoice(int idChoice);
    /**
     * Add All Page of a GameBook in the database
     * @param newGameBook Object GameBook
     */
    void addAllPageGameBook(GameBook newGameBook);
    /**
     * Add All Choices present in a GameBook
     * @param newGameBook Object GameBook
     */
    void addAllChoiceFromPage(GameBook newGameBook);
}
