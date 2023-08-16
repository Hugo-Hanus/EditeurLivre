package org.helmo.gbeditor.presenters;

import javafx.collections.ObservableList;
import org.helmo.gbeditor.domains.Choice;
import org.helmo.gbeditor.domains.GameBook;
import org.helmo.gbeditor.domains.Page;
import org.helmo.gbeditor.infrastructure.storage.storage.LibraryStorage;
import org.helmo.gbeditor.infrastructure.storage.storage.exception.UnableDeleteException;
import org.helmo.gbeditor.infrastructure.storage.storage.exception.UnableToInsertInDb;
import org.helmo.gbeditor.infrastructure.storage.storage.exception.UnableToSelectInDb;
import org.helmo.gbeditor.views.IMainView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Presenter that can edit the content of the Gamebook | Page and Choice
 */
public class InnerGameBookPresenter {
    private final IMainView.IInnerGameBookView innerGameBookView;
    private final MainPresenter mainPresenter;
    private GameBook gameBook;
    private Page currentPage;

    /**
     * Constructor of the GameBook
     *
     * @param innerGameBookView Interface of the  IMainView IInnerGameBookView
     * @param mainPresenter     Object MainPresenter
     */
    public InnerGameBookPresenter(IMainView.IInnerGameBookView innerGameBookView, MainPresenter mainPresenter) {
        this.innerGameBookView = innerGameBookView;
        innerGameBookView.setPresenter(this);
        this.mainPresenter = mainPresenter;

    }

    /**
     * Return to the GameBook list and stop editing content
     */
    public void returnToGameBookList() {
        mainPresenter.sendView(1);
        mainPresenter.setMessage("");
    }

    /**
     * Set the gameBook to be completed
     *
     * @param isbn String isbn of the gameBook chosed
     */
    public void completeBook(String isbn) {
        this.gameBook = mainPresenter.getUser().get(isbn);
        mainPresenter.getPages().clear();
        innerGameBookView.setGameBookEdited(gameBook.getCover().getTitle() + " | " + gameBook.getCover().getIsbn());
    }

    /**
     * Update the List of Page in the view of the gamebook chosed
     *
     * @param oPageList List of String of Page formatted
     * @return List of String formatted with the page of the gameBook
     */
    public List<String> updatePageListView(List<String> oPageList) {
        oPageList.clear();
        gameBook.clearPage();
        mainPresenter.getPages().clear();
        try (LibraryStorage storage = mainPresenter.getFactoryStorage().newStorage(mainPresenter.getUsers(), mainPresenter.getLibrary(), mainPresenter.getPages(), mainPresenter.getChoices())) {
            storage.loadPages(gameBook);
            for (Page pageFromStorage : mainPresenter.getPages().getPagesMap().values()) {
                gameBook.addPage(pageFromStorage);
                oPageList.add(pageFromStorage.toDetail());
            }

        } catch (UnableToSelectInDb e) {
            mainPresenter.setMessage("Impossible de récupérer les pages depuis la BD");
        }
        Collections.sort(oPageList);
        return oPageList;
    }

    /**
     * Update the List of Choice in the view of the page chosed
     *
     * @param oChoiceList List of String of Choice formatted
     * @return List of String formatted with the choice of the page
     */
    public List<String> updateChoiceListView(ObservableList<String> oChoiceList) {
        oChoiceList.clear();
        currentPage.clear();
        mainPresenter.getChoices().clear();
        try (LibraryStorage storage = mainPresenter.getFactoryStorage().newStorage(mainPresenter.getUsers(), mainPresenter.getLibrary(), mainPresenter.getPages(), mainPresenter.getChoices())) {
            storage.loadChoice(currentPage);
            for (Choice choice : mainPresenter.getChoices().getChoicesMap().values()) {
                currentPage.addChoice(choice);
                oChoiceList.add(choice.toChoiceOption());
            }
        } catch (UnableToSelectInDb e) {
            mainPresenter.setMessage("Impossible de récupérer les pages depuis la BD");
        }
        Collections.sort(oChoiceList);
        return oChoiceList;
    }

    /**
     * Add page to the GameBook
     *
     * @param numberPage Integer  number of the Page to add
     * @param text       String text of the page to add
     */
    public void addPageToGameBook(Integer numberPage, String text) {
        if (!text.isEmpty()) {
            if (numberPage == 0 || numberPage > gameBook.getNumberOfPage()) {
                addPageToEnd(gameBook.getNumberOfPage() + 1, text);
            } else {
                addPagetoInsert(numberPage, text);
            }
        } else {
            mainPresenter.setMessage("Champs de texte vide");
        }
    }

    private void addPagetoInsert(Integer value, String text) {
        try (LibraryStorage storage = mainPresenter.getFactoryStorage().newStorage(mainPresenter.getUsers(), mainPresenter.getLibrary(), mainPresenter.getPages(), mainPresenter.getChoices())) {
            Page newPage = new Page(text, value, new ArrayList<Choice>());
            storage.addExistPage(newPage, mainPresenter.getLibrary().getId(gameBook));
            mainPresenter.setMessage("Page ajouté");
        } catch (UnableToInsertInDb e) {
            mainPresenter.setMessage("Impossible d'ajouter la page");
        }
    }

    private void addPageToEnd(int numberPage, String text) {
        try (LibraryStorage storage = mainPresenter.getFactoryStorage().newStorage(mainPresenter.getUsers(), mainPresenter.getLibrary(), mainPresenter.getPages(), mainPresenter.getChoices())) {
            storage.addPage(new Page(text, numberPage, new ArrayList<>()), mainPresenter.getLibrary().getId(gameBook));
            mainPresenter.setMessage("Page ajouté");
        } catch (UnableToInsertInDb e) {
            mainPresenter.setMessage("Impossible d'ajouter la page");
        }

    }

    /**
     * Check the way to delete the Page
     *
     * @param indexPageChoose Interger index of the page to delete
     */
    public void deletingPage(int indexPageChoose) {
            if (gameBook.uniquePage()) {
                deleteUniquePage(mainPresenter.getPages().getId(gameBook.getMapPage().values().iterator().next()));
            } else {
                Page pageToDelete = gameBook.getMapPage().get(indexPageChoose + 1);
                if (pageToDelete.isEmpty()) {
                    deletePageInDb(pageToDelete);
                } else {
                    if (mainPresenter.showConfirmation("Cette page est référencée dans 1 autre(s) page(s), êtes-vous sûr ? »")) {
                        deletePageInDb(pageToDelete);
                    } else {
                        mainPresenter.setMessage("Supression annulé");
                    }
                }
            }


    }

    private void deletePageInDb(Page pageToDelete) {
        deleteChoicePage(mainPresenter.getPages().getId(pageToDelete));
        updatePageDeleted(pageToDelete);
        deleteUniquePage(mainPresenter.getPages().getId(pageToDelete));
    }

    private void updatePageDeleted(Page pagetoDelete) {
        try (LibraryStorage storage = mainPresenter.getFactoryStorage().newStorage(mainPresenter.getUsers(), mainPresenter.getLibrary(), mainPresenter.getPages(), mainPresenter.getChoices())) {
            storage.updateDeletedPage(mainPresenter.getPages().getId(pagetoDelete), mainPresenter.getLibrary().getId(gameBook), pagetoDelete.getNumero());
        } catch (UnableDeleteException e) {
            mainPresenter.setMessage("Impossible de supprimer la page");
        }
    }

    private void deleteChoicePage(int id) {
        try (LibraryStorage storage = mainPresenter.getFactoryStorage().newStorage(mainPresenter.getUsers(), mainPresenter.getLibrary(), mainPresenter.getPages(), mainPresenter.getChoices())) {
            storage.deleteChoicePage(id);
            mainPresenter.setMessage("Choix de la page supprimer");
        } catch (UnableToInsertInDb e) {
            mainPresenter.setMessage("Impossible de supprimer la page");
        }
    }

    private void deleteUniquePage(int id) {
        try (LibraryStorage storage = mainPresenter.getFactoryStorage().newStorage(mainPresenter.getUsers(), mainPresenter.getLibrary(), mainPresenter.getPages(), mainPresenter.getChoices())) {
            storage.deletePage(id);
            mainPresenter.setMessage("Page supprimé");
        } catch (UnableDeleteException e) {
            mainPresenter.setMessage("Impossible de suprimmée la page");
        }
    }

    /**
     * Checking the pages to add the choice
     *
     * @param pageNumber Integer index of the Choice to add
     * @param text       String text of the Choice to add
     */
    public void addChoiceInGameBook(Integer pageNumber, String text) {
        if (mainPresenter.getPages().count() == 0) {
            mainPresenter.setMessage("Il n'y a aucune page dans votre Livre");
        } else {
            addChoiceVerification(pageNumber, text);
        }
    }

    private void addChoiceVerification(Integer pageNumber, String text) {
        if (!text.isEmpty() || pageNumber == 0) {
            if (pageNumber != currentPage.getNumero()) {
                if (pageNumber > gameBook.getNumberOfPage()) {
                    mainPresenter.setMessage("Vous référez une page qui n'existe pas");
                } else {
                    Choice newChoice = new Choice(currentPage.getNumero(),pageNumber, text);
                    addChoiceToPage(mainPresenter.getPages().getId(currentPage), newChoice, mainPresenter.getPages().getId(gameBook.getMapPage().get(pageNumber)));
                }
            } else {
                mainPresenter.setMessage("Le Numéro redirige vers la même page");
            }
        } else {
            mainPresenter.setMessage("Champs vides");
        }

    }

    private void addChoiceToPage(int idFrom, Choice choice, int idTo) {
        try (LibraryStorage storage = mainPresenter.getFactoryStorage().newStorage(mainPresenter.getUsers(), mainPresenter.getLibrary(), mainPresenter.getPages(), mainPresenter.getChoices())) {
            storage.addChoice(choice, idFrom, idTo);
            mainPresenter.setMessage("Choix ajouté");
        } catch (UnableToInsertInDb e) {
            mainPresenter.setMessage("Impossible d'ajouter le choix");
        }
    }

    /**
     * Delete the Choice selected
     *
     * @param element String Choice selected
     */
    public void deleteChoice(String element) {
        if (element != null) {
            String text = element.substring(0, element.indexOf("\n-->"));
            try (LibraryStorage storage = mainPresenter.getFactoryStorage().newStorage(mainPresenter.getUsers(), mainPresenter.getLibrary(), mainPresenter.getPages(), mainPresenter.getChoices())) {
                storage.deleteChoice(mainPresenter.getChoices().getId(currentPage.getChoiceByText(text)));
                mainPresenter.setMessage("Choix supprimé");
            } catch (UnableDeleteException e) {
                mainPresenter.setMessage("Impossible de supprimer le choix");
            }
        } else {
            mainPresenter.setMessage("Aucun choix selectionné");
        }
    }

    /**
     * Set the current page to be edited
     *
     * @param selectedIndex Integer Index of the page selected in the view
     */
    public void setPageEdit(int selectedIndex) {
        this.currentPage = gameBook.getMapPage().get(selectedIndex + 1);
    }

    public GameBook getGameBook() {
        return gameBook;
    }

    public void setGameBook(GameBook gameBook) {
        this.gameBook = gameBook;
    }

    public void setCurrentPage(Page currentPage) {
        this.currentPage = currentPage;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public IMainView.IInnerGameBookView getInnerGameBookView() {
        return innerGameBookView;
    }

    public MainPresenter getMainPresenter() {
        return mainPresenter;
    }
}
