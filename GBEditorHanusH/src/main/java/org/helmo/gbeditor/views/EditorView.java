package org.helmo.gbeditor.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.helmo.gbeditor.presenters.EditorGameBookPresenter;


public class EditorView implements IMainView.IEditorView {

    private EditorGameBookPresenter editorPresenter;
    private static final String CREATE = "Créer le livre";

    private ObservableList<String> oListGameBook = FXCollections.observableArrayList();
    private final ListView<String> gameBookInList = new ListView<>();

    {
        gameBookInList.setItems(oListGameBook);
        gameBookInList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String element = gameBookInList.getSelectionModel().getSelectedItem();
                if (event.getClickCount() == 2) {
                    editorPresenter.setCurrentGameBookToRead(element);
                }
            }
        });
    }

    private final ViewCreateBP viewCreateBP = new ViewCreateBP();
    private final ViewCreateBookBP viewCreateBookBP = new ViewCreateBookBP(CREATE);
    private final ViewEditBookBP viewEditBookBP = new ViewEditBookBP("", "", "", "", false,false);
    private final EditorViewBP editorViewBP = new EditorViewBP(viewCreateBP, viewCreateBookBP, viewEditBookBP);


    /***
     * Internal Class with a listView and button to interact with a GameBook (Create / export)
     */
    public class ViewCreateBP extends BorderPane {
        /**
         * Constructor of BorderPane to have a view on the mainPane
         */
        public ViewCreateBP() {
            Label create = new Label("Créer un livre : ") {
            };
            Button createBookButton = new Button("Créer");
            {
                createBookButton.getStyleClass().add("add");
                createBookButton.setOnAction(action -> editorPresenter.sendStackView(1, ""));
            }


            Button editBookButton = new Button("Éditer un livre");
            {
                editBookButton.getStyleClass().add("edit");
                editBookButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String choose = gameBookInList.getSelectionModel().getSelectedItem();
                        if (choose == null) {
                            editorPresenter.sendStackView(0, "Aucun Livre Choisi");
                        } else if (!choose.startsWith("Publié-")) {
                            editorPresenter.editingBook(choose.substring(0, 13));
                            oListGameBook = (ObservableList<String>) editorPresenter.updateListView(oListGameBook);
                        } else {
                            editorPresenter.sendStackView(0, "Livre publié - Impossible à modifier");
                        }
                    }
                });
            }
            Button publishBookButton = new Button("Publier un livre");
            {
                publishBookButton.getStyleClass().add("publish");
                publishBookButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String choose = gameBookInList.getSelectionModel().getSelectedItem();
                        editorPresenter.publishGameBook(choose);
                        oListGameBook = (ObservableList<String>) editorPresenter.updateListView(oListGameBook);
                    }
                });
            }
            Button reeditButton = new Button("Rééditer un livre");
            {
                reeditButton.getStyleClass().add("republish");
                reeditButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String choose = gameBookInList.getSelectionModel().getSelectedItem();
                        if (choose == null) {
                            editorPresenter.sendStackView(0, "Aucun Livre Choisi");
                        } else if (choose.startsWith("Publié-")) {
                            editorPresenter.editingBook(choose.substring(7, 20));
                        } else {
                            editorPresenter.sendStackView(0, "Le livre doit être publié");
                        }
                    }
                });
            }

           /*  Button exportJSONButton = new Button("Exporter un livre");{
                exportJSONButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        MultipleSelectionModel<String> listViewModel= vueEnList.getSelectionModel();
                        String choose=listViewModel.getSelectedItem();
                        presenter.exportOne(choose);
                    }
                });
            }
         PAS DANS LES US
            Button importOneJSONButton = new Button("Importer");{
                importOneJSONButton.setOnAction(action->{
                    presenter.importOne();
                    String lastKey=presenter.getLibrary().lastKey();
                    oListe.add(presenter.getLibrary().get(lastKey).getGameBook());
                });
            }*/

            VBox hbox = new VBox(gameBookInList) {
            };
            VBox edit = new VBox(editBookButton, publishBookButton, reeditButton);
            VBox menu = new VBox();
            {
                menu.getChildren().add(hbox);
                menu.getChildren().add(create);
                menu.getChildren().add(createBookButton);
            }
            setCenter(menu);
            setRight(edit);
        }
    }


    /***
     *  Internal Class with field to create a GameBook
     */
    public class ViewCreateBookBP extends BorderPane {
        /***
         * Constructor of BorderPane to create a GameBook
         */
        public ViewCreateBookBP(String actionButton) {
            Label title = new Label("Titre : ");
            TextField titleEntry = new TextField("");
            Label isbn = new Label("ISBN : ");
            TextField isbnEntry = new TextField("");
            Label resume = new Label("Resume : ");
            TextArea resumeEntry = new TextArea("");
            Button returnButton = new Button("Retour");
            {
                returnButton.getStyleClass().add("edit");
                returnButton.setOnAction(event -> {
                    clearField(titleEntry, isbnEntry, resumeEntry);
                    editorPresenter.sendStackView(0, "Création annulée");
                    oListGameBook = (ObservableList<String>) editorPresenter.updateListView(oListGameBook);
                });
            }
            Button createBookButton = new Button(actionButton);
            {
                createBookButton.getStyleClass().add("add");
                createBookButton.setOnAction(event -> {
                    if (checkEntryCreateBook(titleEntry.getText(), isbnEntry.getText(), resumeEntry.getText())) {
                        editorPresenter.newBook(titleEntry.getText(), isbnEntry.getText(), resumeEntry.getText(), false);
                        oListGameBook = (ObservableList<String>) editorPresenter.updateListView(oListGameBook);
                        clearField(titleEntry, isbnEntry, resumeEntry);
                    } else {
                        editorPresenter.sendStackView(1, "Champ vides");
                    }
                });
            }
            VBox creationBook = new VBox();
            {
                creationBook.getChildren().add(title);
                creationBook.getChildren().add(titleEntry);
                creationBook.getChildren().add(isbn);
                creationBook.getChildren().add(isbnEntry);
                creationBook.getChildren().add(resume);
                creationBook.getChildren().add(resumeEntry);
                creationBook.getChildren().add(createBookButton);
                creationBook.getChildren().add(returnButton);
            }
            setCenter(creationBook);
        }
    }

    /***
     *  Internal Class with field to edit a GameBook
     */
    public class ViewEditBookBP extends BorderPane {
        /***
         * Constructor of BorderPane to edit  a GameBook
         */
        public ViewEditBookBP(String titleGB, String isbnGB, String resumeGB, String choose, boolean publish,boolean edited) {
            Label title = new Label("Titre : ");
            TextField titleEntry = new TextField(titleGB);
            Label isbn = new Label("ISBN : ");
            TextField isbnEntry = new TextField(isbnGB);
            Label resume = new Label("Resume : ");
            TextArea resumeEntry = new TextArea(resumeGB);
            Button returnButton = new Button("Retour");
            {
                returnButton.getStyleClass().add("delete");
                returnButton.setOnAction(event -> {
                    clearField(titleEntry, isbnEntry, resumeEntry);
                    oListGameBook = (ObservableList<String>) editorPresenter.updateListView(oListGameBook);
                    editorPresenter.sendStackView(0, "Edition annulé");
                });
            }
            Button editeBookButton = new Button("Editer");
            {
                editeBookButton.getStyleClass().add("add");
                editeBookButton.setOnAction(event -> {
                    if (checkEntryCreateBook(titleEntry.getText(), isbnEntry.getText(), resumeEntry.getText())) {
                        editorPresenter.editGameBook(choose, titleEntry.getText(), isbnEntry.getText(), resumeEntry.getText());
                        editorPresenter.updateListView(oListGameBook);
                        clearField(titleEntry, isbnEntry, resumeEntry);
                        editorPresenter.sendStackView(0, "");
                    } else {
                        editorPresenter.sendStackView(2, "Certains champs sont vides ou dépassent la taille demandée");
                    }
                });
            }
            Button reediteBookButton = new Button("Rééditer");
            {
                reediteBookButton.getStyleClass().add("add");
                reediteBookButton.setOnAction(event -> {
                    if (checkEntryCreateBook(titleEntry.getText(), isbnEntry.getText(), resumeEntry.getText())) {
                        editorPresenter.reEditingBook(isbnGB, isbnEntry.getText());
                        clearField(titleEntry, isbnEntry, resumeEntry);
                        oListGameBook= (ObservableList<String>) editorPresenter.updateListView(oListGameBook);
                        editorPresenter.sendStackView(0, "");
                    } else {
                        editorPresenter.sendStackView(2, "Certains champs sont vides ou dépassent la taille demandée");
                    }
                });
            }
            VBox editionBook = new VBox();
            {

                editionBook.getChildren().add(title);
                editionBook.getChildren().add(titleEntry);
                editionBook.getChildren().add(isbn);
                editionBook.getChildren().add(isbnEntry);
                editionBook.getChildren().add(resume);
                editionBook.getChildren().add(resumeEntry);
                editionBook.getChildren().add(editeBookButton);
                editionBook.getChildren().add(reediteBookButton);
                editionBook.getChildren().add(returnButton);
            }
            if (publish) {
                editionBook.getChildren().get(0).setVisible(false);
                editionBook.getChildren().get(1).setVisible(false);
                editionBook.getChildren().get(4).setVisible(false);
                editionBook.getChildren().get(5).setVisible(false);
                editionBook.getChildren().get(6).setVisible(false);
                editionBook.getChildren().get(8).setVisible(true);
            } else if(edited){
                editionBook.getChildren().get(0).setVisible(false);
                editionBook.getChildren().get(1).setVisible(false);
                editionBook.getChildren().get(7).setVisible(false);
            }else{
                editionBook.getChildren().get(7).setVisible(false);
            }

            setCenter(editionBook);
        }
    }


    public class EditorViewBP extends StackPane {

        public EditorViewBP(ViewCreateBP createBP, ViewCreateBookBP createBookBP, ViewEditBookBP editBookBP) {
            getChildren().addAll(createBP, createBookBP, editBookBP);
            getChildren().get(1).setVisible(false);
            getChildren().get(2).setVisible(false);
        }
    }


    public StackPane getEditorViewBP() {
        return editorViewBP;
    }


    private boolean checkEntryCreateBook(String titleEntry, String isbnEntry, String resumeEntry) {
        return (!titleEntry.trim().isEmpty()) && (!isbnEntry.trim().isEmpty()) && (!resumeEntry.trim().isEmpty()) && (verifyLength(titleEntry, 150) && verifyLength(resumeEntry, 500));
    }

    private boolean verifyLength(String text, int length) {
        return text.length() < length;
    }


    /***
     * Clear field of a StackPane
     * @param fields All field how wants to be clear
     */
    public void clearField(TextInputControl... fields) {
        for (TextInputControl field : fields) {
            field.clear();
        }
    }

    @Override
    public void setPresenter(EditorGameBookPresenter editorPresenter) {
        this.editorPresenter = editorPresenter;
    }

    @Override
    public void goToEditorView(int index) {
        int stackLength = editorViewBP.getChildren().size();
        for (int i = 0; i < stackLength; i++) {
            editorViewBP.getChildren().get(i).setVisible(false);
        }
        editorViewBP.getChildren().get(index).setVisible(true);
    }

    @Override
    public void setFielToEdit(String choose, String title, String isbn, String resume, boolean publish,boolean edited) {
        if (editorViewBP.getChildren().size() == 3) {
            editorViewBP.getChildren().remove(2);
        }
        editorViewBP.getChildren().add(new ViewEditBookBP(choose, title, isbn, resume, publish,edited));
    }

    @Override
    public void setGameBookList() {
        oListGameBook = (ObservableList<String>) editorPresenter.updateListView(oListGameBook);
    }




}
