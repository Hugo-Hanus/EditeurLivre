package org.helmo.gbeditor.views;

import javafx.scene.control.ListView;
import org.helmo.gbeditor.presenters.EditorGameBookPresenter;
import org.helmo.gbeditor.presenters.InnerGameBookPresenter;
import org.helmo.gbeditor.presenters.LoginPresenter;
import org.helmo.gbeditor.presenters.MainPresenter;

public interface IMainView {

    void setUser(String msg);

    /**
     * Associe un presenter à la vue
     */
    void setPresenters(MainPresenter presenter);

    /**
     * Affiche un message à l'utilisateur
     * @param s texte du message
     */
    void sendMessage(String s);


    /**
     * Change de panneau
     * @param index le nom du panneau de destination
     */
    void goTo(int index);

    boolean showConfirmation(String message);


    interface ILoginView {
        void setPresenter(LoginPresenter loginPresenter);
    }

    interface IEditorView{
        void setPresenter(EditorGameBookPresenter editorPresenter);

        void goToEditorView(int index);
        void setFielToEdit(String choose, String title,String isbn, String resume,boolean publish,boolean edited);
        void setGameBookList();

    }

    interface IInnerGameBookView{

        void setPresenter(InnerGameBookPresenter innerGameBookPresenter);

        void setGameBookEdited(String isbn);
    }


    ILoginView getILoginView();
    IEditorView getIEditorView();
    IInnerGameBookView getInnerGameBookView();


}
