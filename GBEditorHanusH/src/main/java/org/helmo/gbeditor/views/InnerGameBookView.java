package org.helmo.gbeditor.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.helmo.gbeditor.presenters.InnerGameBookPresenter;


public class InnerGameBookView implements IMainView.IInnerGameBookView {

    private InnerGameBookPresenter innerGameBookPresenter;
    private ObservableList<String> oPageList = FXCollections.observableArrayList();
    private ObservableList<String> oChoiceList = FXCollections.observableArrayList();

    private final ListView<String> pageInList = new ListView<>();

    {
        pageInList.setItems(oPageList);
        pageInList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    String element = pageInList.getSelectionModel().getSelectedItem();
                    if (element != null) {
                        viewChoiceBP.setLabelPage(element.substring(element.indexOf(':') + 1, element.indexOf('|') - 1));
                        innerGameBookPresenter.setPageEdit(pageInList.getSelectionModel().getSelectedIndex());
                        oChoiceList = (ObservableList<String>) innerGameBookPresenter.updateChoiceListView(oChoiceList);
                    }
                }
            }
        });
    }

    private final ListView<String> choiceInList = new ListView<>();

    {
        choiceInList.setItems(oChoiceList);
    }

    private final ViewPageBP viewPageBP = new ViewPageBP();
    private final ViewChoiceBP viewChoiceBP = new ViewChoiceBP();
    private final InnerViewBP innerViewBP = new InnerViewBP(viewPageBP, viewChoiceBP);

    @Override
    public void setPresenter(InnerGameBookPresenter innerGameBookPresenter) {
        this.innerGameBookPresenter = innerGameBookPresenter;
    }

    @Override
    public void setGameBookEdited(String isbn) {
        innerViewBP.setGameBookTitle(isbn);
        oPageList = (ObservableList<String>) innerGameBookPresenter.updatePageListView(oPageList);
        oChoiceList.clear();
    }


    /**
     * ------------------ PAGE --------------------------------------------------
     * /***
     * Internal Class with field to authenticate the User using this Program
     */
    public class ViewPageBP extends BorderPane {
        /***
         * Constructor of BorderPane to connect the user
         */
        public ViewPageBP() {

            Label numero = new Label("Numero : ");
            Spinner<Integer> spinner = new Spinner<>(0, 100, 0);
            Label textePage = new Label("Texte de la Page : ");
            TextArea textePageEntry = new TextArea("");
            Button pageAddPageButton = new Button("Ajout de la Page");
            {
                pageAddPageButton.setOnAction(action -> {
                    innerGameBookPresenter.addPageToGameBook(spinner.getValue(), textePageEntry.getText());
                    oPageList = (ObservableList<String>) innerGameBookPresenter.updatePageListView(oPageList);
                    clearFieldPage(spinner, textePageEntry);
                });
            }
            Button pageDeleteButton = new Button("Suppression de la Page");
            {
                pageDeleteButton.getStyleClass().add("delete");
                pageDeleteButton.setOnAction(action -> {
                    int element = pageInList.getSelectionModel().getSelectedIndex();
                    if(element!=-1) {
                        innerGameBookPresenter.deletingPage(element);
                        oPageList = (ObservableList<String>) innerGameBookPresenter.updatePageListView(oPageList);
                    }
                });
            }
            pageAddPageButton.getStyleClass().add("add");


            VBox listBox = new VBox();
            {
                listBox.getChildren().add(pageInList);
            }
            VBox page = new VBox();
            {
                page.getChildren().add(numero);
                page.getChildren().add(spinner);
                page.getChildren().add(textePage);
                page.getChildren().add(textePageEntry);
                page.getChildren().add(pageAddPageButton);
                page.getChildren().add(pageDeleteButton);
            }
            setLeft(listBox);
            setCenter(page);
        }

        private void clearFieldPage(Spinner<Integer> spinner, TextArea textePageEntry) {
            textePageEntry.setText("");
            spinner.getValueFactory().setValue(0);
        }
    }

    /**
     * --------------------- CHOICE  ---------------------------------------------------------
     * /***
     * Internal Class with field to authenticate the User using this Program
     */
    public class ViewChoiceBP extends BorderPane {
        /***
         * Constructor of BorderPane to connect the user
         */
        private Label labelPage;

        public ViewChoiceBP() {
            labelPage = new Label();
            Label labelChoice = new Label("Vers la Page : ");
            Spinner<Integer> spinnerChoice = new Spinner<>(0, 100, 0);
            Label textChoice = new Label("Texte du Choix : ");
            TextArea texteChoiceEntry = new TextArea("");
            Button choiceAddButton = new Button("Ajout du Choix");
            {
                choiceAddButton.setOnAction(action -> {
                    innerGameBookPresenter.addChoiceInGameBook(spinnerChoice.getValue(), texteChoiceEntry.getText());
                    oChoiceList = (ObservableList<String>) innerGameBookPresenter.updateChoiceListView(oChoiceList);
                    clearFieldChoice(spinnerChoice, texteChoiceEntry);
                });
            }
            Button pageDeleteButton = new Button("Suppression du choix");
            {
                pageDeleteButton.getStyleClass().add("delete");
                pageDeleteButton.setOnAction(action -> {
                    String element = choiceInList.getSelectionModel().getSelectedItem();
                    innerGameBookPresenter.deleteChoice(element);
                    oChoiceList = (ObservableList<String>) innerGameBookPresenter.updateChoiceListView(oChoiceList);
                });
            }
            choiceAddButton.getStyleClass().add("add");


            VBox listBox = new VBox();
            {
                listBox.getChildren().add(choiceInList);
            }
            VBox choix = new VBox();
            {
                choix.getChildren().add(labelPage);
                choix.getChildren().add(textChoice);
                choix.getChildren().add(texteChoiceEntry);
                choix.getChildren().add(labelChoice);
                choix.getChildren().add(spinnerChoice);
                choix.getChildren().add(choiceAddButton);
                choix.getChildren().add(pageDeleteButton);
            }
            setLeft(listBox);
            setCenter(choix);
        }

        private void clearFieldChoice(Spinner<Integer> spinner, TextArea textePageEntry) {
            textePageEntry.setText("");
            spinner.getValueFactory().setValue(0);
        }

        private void setLabelPage(String page) {
            this.labelPage.setText("Page :" + page);
        }
    }


    public class InnerViewBP extends BorderPane {

        private Label gameBookTitle;

        public InnerViewBP(ViewPageBP viewPageBP, ViewChoiceBP viewChoiceBP) {

            gameBookTitle = new Label();
            SplitPane innerGameBookSP = new SplitPane();
            {
                VBox leftSidePage = new VBox(viewPageBP);
                VBox rightSideChoice = new VBox(viewChoiceBP);
                innerGameBookSP.getItems().addAll(leftSidePage, rightSideChoice);
            }
            Button returnButton = new Button("Retour");
            {
                returnButton.setMaxWidth(500);
                returnButton.getStyleClass().add("edit");
                returnButton.setOnAction(event -> {
                    innerGameBookPresenter.returnToGameBookList();
                });
            }

            setTop(gameBookTitle);
            setAlignment(gameBookTitle, Pos.TOP_CENTER);
            setCenter(innerGameBookSP);
            setBottom(returnButton);
            setAlignment(returnButton, Pos.BOTTOM_CENTER);
        }


        public void setGameBookTitle(String gameBookTitle) {
            this.gameBookTitle.setText(gameBookTitle);
            viewChoiceBP.setLabelPage("");
        }
    }

    public BorderPane getInnerGameBookBP() {

        return innerViewBP;
    }


}
