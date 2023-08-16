package org.helmo.gbeditor.views;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.helmo.gbeditor.presenters.LoginPresenter;

public class LoginView implements IMainView.ILoginView{

    private LoginPresenter loginPresenter;

    @Override
    public void setPresenter(LoginPresenter loginPresenter) {
        this.loginPresenter=loginPresenter;
    }

    /***
     * Internal Class with field to authenticate the User using this Program
     */
    public class ViewAuthenticationBP extends BorderPane {
        /***
         * Constructor of BorderPane to connect the user
         */
        public ViewAuthenticationBP(){
            Label nom = new Label("Nom : ");
            TextField nomEntry= new TextField("");
            Label firstname = new Label("Prenom : ");
            TextField firstnameEntry= new TextField("");
            Button connectUserButton = new Button("Connexion");{
                connectUserButton.setOnAction(action->{
                    loginPresenter.login(firstnameEntry.getText(),nomEntry.getText());

                });
            }
            VBox connection = new VBox();{
                connection.getChildren().add(nom);
                connection.getChildren().add(nomEntry);
                connection.getChildren().add(firstname);
                connection.getChildren().add(firstnameEntry);
                connection.getChildren().add(connectUserButton);
            }
            setCenter(connection);
        }
    }

    public ViewAuthenticationBP getLoginViewBP() {
        return new ViewAuthenticationBP();
    }
}
