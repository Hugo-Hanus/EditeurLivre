package org.helmo.gbeditor.views;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.helmo.gbeditor.presenters.MainPresenter;

import java.util.Optional;

public class MainView implements IMainView{

    private  MainPresenter mainPresenter;
    private LoginView loginView=new LoginView();
    private EditorView editorView=new EditorView();
    private InnerGameBookView innerGameBookView = new InnerGameBookView();
    private final Label messageH = new Label("Message : ");
    private final Label message = new Label("");
    private final Label user = new Label("Utilisateur : ");
    private final Label userName = new Label("");



    private static final StackPane centerStack = new StackPane();{
       centerStack.getChildren().addAll(loginView.getLoginViewBP(),editorView.getEditorViewBP(),innerGameBookView.getInnerGameBookBP());
    }
    private static final VBox messageBox = new VBox();{
        messageBox.getChildren().add(messageH);
        messageBox.getChildren().add(message);
    }
    private static final  HBox userBox = new HBox();{
        userBox.getChildren().add(user);
        userBox.getChildren().add(userName);
    }
    private static final BorderPane mainPane = new BorderPane();{
        mainPane.setTop(userBox);
        mainPane.setCenter(centerStack);
        mainPane.setBottom(messageBox);
    }
    /***
     * MainView get rooted to the MainPane
     * @return Parent of the mainPane
     */
    public Parent getRoot(){
        return mainPane;
    }

    @Override
    public void setUser(String msg) {
        userName.setText(msg);
    }
    @Override
    public void setPresenters(MainPresenter presenter) {
        this.mainPresenter = presenter;
        mainPresenter.sendView(0);
    }


    @Override
    public void sendMessage(String s) {
        message.setText(s);
    }

    @Override
    public void goTo(int index) {
        Node nodeOut=mainPane.getChildren().get(1);
        int length = ((StackPane) nodeOut).getChildren().size();
        for(int i=0;i<length;i++){
            ((StackPane) nodeOut).getChildren().get(i).setVisible(false);
        }
        ((StackPane)nodeOut).getChildren().get(index).setVisible(true);
    }
    @Override
    public boolean showConfirmation(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la demande");
        alert.setHeaderText(message);
        Optional<ButtonType> option = alert.showAndWait();
        if(option.get()==ButtonType.OK){
            return true;
        }else if(option.get()==ButtonType.CANCEL){
            return false;
        }else{
            return false;
        }
    }

    @Override
    public ILoginView getILoginView() {
        return loginView;
    }

    @Override
    public IEditorView getIEditorView() {
        return editorView;
    }

    @Override
    public IInnerGameBookView getInnerGameBookView() {
        return innerGameBookView;
    }



}
