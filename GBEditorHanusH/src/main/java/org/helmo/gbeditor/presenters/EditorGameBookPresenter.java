package org.helmo.gbeditor.presenters;


import org.helmo.gbeditor.domains.*;
import org.helmo.gbeditor.domains.exception.UnableToPublishGameBookException;
import org.helmo.gbeditor.infrastructure.storage.storage.LibraryStorage;
import org.helmo.gbeditor.infrastructure.storage.storage.exception.*;
import org.helmo.gbeditor.views.IMainView;

import java.util.*;

/**
 * Presenter that can edit the cover of the GameBook and publish it
 */
public class EditorGameBookPresenter {

    private final IMainView.IEditorView editorView;
    private final MainPresenter mainPresenter;

    /**
     * Constructor of EditorGameBookPresenter
     *
     * @param editorView    Interface of the  IMainView IEditorView
     * @param mainPresenter Object MainPresenter
     */
    public EditorGameBookPresenter(IMainView.IEditorView editorView, MainPresenter mainPresenter) {
        this.editorView = editorView;
        this.mainPresenter = mainPresenter;
        editorView.setPresenter(this);

    }


    /**
     * Put the StackView in front of the EditorView with a message
     *
     * @param index   Integer index of the stackView
     * @param message String message
     */
    public void sendStackView(int index, String message) {
        editorView.goToEditorView(index);
        if (!message.isEmpty()) {
            mainPresenter.setMessage(message);
        }

    }

    /**
     * Create a new Book if isbn not correct set a message
     *
     * @param title  String title of the GameBook
     * @param isbn   String ISBN of the GameBook
     * @param resume String resume if the GameBook
     */
    public boolean newBook(String title, String isbn, String resume, boolean isEdited) {
        Cover newCover = new Cover(title, isbn, resume);
        if (newCover.checkISBN()) {
            try {
                addBook(newCover, isEdited);
                return true;
            } catch (UnableToInsertInDb | UnableToSetupException e) {
                mainPresenter.setMessage("Impossbile de créer le Livre dans la base de donnée");
                return false;
            }
        } else {
            mainPresenter.setMessage(" ISBN: Mauvais format 9-999999-99-9 ");
            return false;
        }
    }

    private void addBook(Cover cover, boolean isEdited) {
        try (LibraryStorage storage = mainPresenter.getFactoryStorage().newStorage(mainPresenter.getUsers(), mainPresenter.getLibrary(), mainPresenter.getPages(), mainPresenter.getChoices())) {
            GameBook newBook = new GameBook(cover, false, isEdited, new HashMap<Integer, Page>());
            storage.loadGameBooks(mainPresenter.getUser());
            storage.addGamebook(newBook, mainPresenter.getUser());
            sendStackView(0, "Le livre " + newBook.getCover().getTitle() + " a été crée");
        } catch (UnableToSetupException | UnableToInsertInDb e) {
            throw new UnableToInsertInDb(e.getMessage());
        }
    }

    /**
     * Update the List view of the Gamebook of the user
     *
     * @param oList List<String> with all gamebook of the user
     * @return List of string with all gamebook of the user
     */
    public List<String> updateListView(List<String> oList) {
        oList.clear();
        User currentUser = mainPresenter.getUser();
        currentUser.clearAllGameBook();
        try (LibraryStorage storage = mainPresenter.getFactoryStorage().newStorage(mainPresenter.getUsers(), mainPresenter.getLibrary(), mainPresenter.getPages(), mainPresenter.getChoices())) {
            storage.loadGameBooks(currentUser);
            for (GameBook gb : currentUser.getLibrary().values()) {
                oList.add(gb.formatGameBook(gb.isPublish()) + " || " + currentUser.formatUser());
            }
        } catch (UnableToSetupException e) {
            throw new UnableToConnectToDb(e);
        }
        if (oList.isEmpty()) {
            mainPresenter.setMessage("Vous n'avez aucun livre");
        }
        Collections.sort(oList);
        return oList;
    }

    /**
     * Go to the with field fill to edit the gamebook chosed
     *
     * @param choose String gamebook chosed
     */
    public void editingBook(String choose) {
        mainPresenter.setMessage("");
        GameBook gameBook = mainPresenter.getUser().get(choose);
        Cover cover = gameBook.getCover();
        editorView.setFielToEdit(cover.getTitle(), cover.getIsbn(), cover.getResume(), choose, gameBook.isPublish(),gameBook.isEdited());
        sendStackView(2, "");
    }

    /**
     * Edit the gameBook
     *
     * @param choose String GameBook chosed
     * @param title  String new title
     * @param isbn   String  new isbn
     * @param resume String new resume
     */
    public void editGameBook(String choose, String title, String isbn, String resume) {
        if (mainPresenter.getUser().get(choose).getCover().checkISBN()) {
            try (LibraryStorage storage = mainPresenter.getFactoryStorage().newStorage(mainPresenter.getUsers(), mainPresenter.getLibrary(), mainPresenter.getPages(), mainPresenter.getChoices())) {
                storage.updateGameBookInfo(mainPresenter.getUser().get(choose), title, isbn, resume);
                mainPresenter.setMessage("Le Livre a été édité");
            } catch (UnableToUpdateInformation e) {
                mainPresenter.setMessage("ISBN existe déjà dans la BD");
            }
        } else {
            mainPresenter.setMessage("ISBN n'a pas le bon format ou le chiffre de sécurité n'est pas correct");
        }
    }

    /**
     * Reedit the gamebook publish
     *
     * @param publishIsbn String gamebookIsbn chosed
     * @param newIsbn     String newIsbn created
     */
    public void reEditingBook(String publishIsbn, String newIsbn) {
        GameBook publishGameBook = mainPresenter.getLibrary().getGameBookByIsbn(publishIsbn);
        if (newBook(publishGameBook.getCover().getTitle(), newIsbn, publishGameBook.getCover().getResume(), true)) {
            GameBook newGameBook = mainPresenter.getUser().get(newIsbn);
            loadPublishBook(publishGameBook, newGameBook);
            savePageNewGameBook(newGameBook);
            saveChoiceNewGameBook(newGameBook);

        } else {
            mainPresenter.setMessage("Mauvais ISBN");
        }


    }


    private void loadPublishBook(GameBook publishGameBook, GameBook newGameBook) {
        try (LibraryStorage storage = mainPresenter.getFactoryStorage().newStorage(mainPresenter.getUsers(), mainPresenter.getLibrary(), mainPresenter.getPages(), mainPresenter.getChoices())) {
            storage.loadPages(publishGameBook);
            for (Page page : publishGameBook.getMapPage().values()) {
                storage.loadChoice(page);
                newGameBook.addPage(page);
            }
        } catch (UnableToSelectInDb e) {
            mainPresenter.setMessage("Impossible de récuperé les données depuis la BD");
        }
    }

    private void savePageNewGameBook(GameBook newGameBook) {
        try (LibraryStorage storage = mainPresenter.getFactoryStorage().newStorage(mainPresenter.getUsers(), mainPresenter.getLibrary(), mainPresenter.getPages(), mainPresenter.getChoices())) {
                storage.addAllPageGameBook(newGameBook);
        } catch (UnableToInsertInDb e) {
            mainPresenter.setMessage("Impossible insérer les pages données dans la BD ");
        }
    }

    private void saveChoiceNewGameBook(GameBook newGameBook) {
        try (LibraryStorage storage = mainPresenter.getFactoryStorage().newStorage(mainPresenter.getUsers(), mainPresenter.getLibrary(), mainPresenter.getPages(), mainPresenter.getChoices())) {
            storage.addAllChoiceFromPage(newGameBook);
        } catch (UnableToInsertInDb e) {
            mainPresenter.setMessage("Impossible insérer les choix données dans la BD");
        }

    }


    /**
     * Publish the GameBook
     *
     * @param choose String gamebook chosed
     */
    public void publishGameBook(String choose) {
        if (choose == null) {
            mainPresenter.setMessage("Vous n'avez pas sélectionnez de livre ");
        } else if (!choose.startsWith("Publié")) {
            if (mainPresenter.showConfirmation("Voulez-vous confirmer la publication du livre ?")) {
                publishingGameBook(choose.substring(0, 13));
            } else {
                mainPresenter.setMessage("Publication annulée");
            }
        } else {
            mainPresenter.setMessage("Livre déjà publié");
        }
    }

    private void publishingGameBook(String choose) {
            try (LibraryStorage storage = mainPresenter.getFactoryStorage().newStorage(mainPresenter.getUsers(), mainPresenter.getLibrary(), mainPresenter.getPages(), mainPresenter.getChoices())) {
                storage.loadPages(mainPresenter.getUser().get(choose));
                verificationUpdate(choose, storage);
                mainPresenter.setMessage("Le livre à été publié");
            } catch (UnableToPublishGameBookException e) {
                mainPresenter.setMessage("Erreur lors de la publication du livre");
            }
    }

    private void verificationUpdate(String choose, LibraryStorage storage) {
        if (mainPresenter.getUser().get(choose).isPublish()) {
            throw new UnableToPublishGameBookException("Gamebook déjà publie");
        } else if (mainPresenter.getUser().get(choose).isEmpty()) {
            throw new UnableToPublishGameBookException("Gamebook contenant aucune page");
        } else {
            storage.publishGameBook(mainPresenter.getUser().get(choose));
        }
    }

    /**
     * Set the book chosed to be completed with pages and choices
     *
     * @param element String gameBook chosed
     */
    public void setCurrentGameBookToRead(String element) {
        if (element == null) {
            mainPresenter.setMessage("Aucun livre sélectioné");
        } else if (element.startsWith("Publié")) {
            mainPresenter.setMessage("Livre déjà publié");
        } else {
            mainPresenter.setMessage("");
            mainPresenter.sendView(2);
            if (element.startsWith("Réédité")) {
                mainPresenter.completingGameBook(element.substring(7, 21));
            } else {
                mainPresenter.completingGameBook(element.substring(0, 13));
            }
        }
    }

    /**
     * Set The GameBookList to the EditorView
     */
    public void setGameBookList() {
        editorView.setGameBookList();
    }

    public MainPresenter getMainPresenter() {
        return mainPresenter;
    }

    public IMainView.IEditorView getEditorView() {
        return editorView;
    }

}
