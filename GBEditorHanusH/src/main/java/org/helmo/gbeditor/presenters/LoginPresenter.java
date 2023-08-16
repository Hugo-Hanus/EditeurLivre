package org.helmo.gbeditor.presenters;

import org.helmo.gbeditor.domains.User;
import org.helmo.gbeditor.infrastructure.storage.storage.LibraryStorage;

import org.helmo.gbeditor.infrastructure.storage.storage.exception.UnableToSetupException;
import org.helmo.gbeditor.views.IMainView;


import java.util.HashMap;

/**
 *
 *   Presenter that can log the user
 *
 */
public class LoginPresenter {

    private final IMainView.ILoginView loginView;
    private final MainPresenter mainPresenter;

    /**
     *
     * @param loginView
     * @param presenter
     */
    public LoginPresenter(IMainView.ILoginView loginView,MainPresenter presenter){
        this.loginView=loginView;
        this.mainPresenter=presenter;
        loginView.setPresenter(this);
    }

    /**
     *
     * @param firstname
     * @param name
     */
    public void login(String firstname,String name){
        try(LibraryStorage storage = mainPresenter.getFactoryStorage().newStorage(mainPresenter.getUsers(),mainPresenter.getLibrary(),mainPresenter.getPages(),mainPresenter.getChoices())) {
           User user = new User(firstname,name,new HashMap<>());
            if (user.checkEntryConnection(firstname, name)) {
                if (storage.checkIfUserExistOrCreate(user)) {
                    mainPresenter.setUser(user);
                    mainPresenter.sendView(1);
                    mainPresenter.setMessage("");

                } else {
                    mainPresenter.setMessage("Erreur lors de la connexion sur le check");
                }
            }else{
                mainPresenter.setMessage("Un des champs est vide");
            }
        } catch (UnableToSetupException e) {
           mainPresenter.setMessage("Erreur de connexion à la DB");
        }
    }

    public IMainView.ILoginView getLoginView() {
        return loginView;
    }

    public MainPresenter getMainPresenter() {
        return mainPresenter;
    }
}
