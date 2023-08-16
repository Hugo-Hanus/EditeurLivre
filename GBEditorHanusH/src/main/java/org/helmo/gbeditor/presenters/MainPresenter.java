package org.helmo.gbeditor.presenters;

import org.helmo.gbeditor.domains.User;
import org.helmo.gbeditor.infrastructure.storage.dto.Choices;
import org.helmo.gbeditor.infrastructure.storage.dto.Library;
import org.helmo.gbeditor.infrastructure.storage.dto.Pages;
import org.helmo.gbeditor.infrastructure.storage.dto.Users;
import org.helmo.gbeditor.infrastructure.storage.storage.FactoryStorage;
import org.helmo.gbeditor.repository.LibraryRepository;
import org.helmo.gbeditor.views.IMainView;

/**
 * MainPresenter interact with the Interfarce IMainView
 */
public class MainPresenter {


    private final IMainView view;
    private final LoginPresenter loginPresenter;
    private final EditorGameBookPresenter editorPresenter;
    private final InnerGameBookPresenter innerGameBookPresenter;
    private final LibraryRepository repository;
    private final  FactoryStorage factoryStorage;
    private User user;
    private final Users users=new Users();
    private final Library library=new Library();
    private final Pages pages = new Pages();
    private final Choices choices =new Choices();

    /**
     *
     * @param mainView
     * @param repository
     * @param factoryStorage
     */
    public MainPresenter(IMainView mainView, LibraryRepository repository, FactoryStorage factoryStorage){
        this.view= mainView;
        this.repository=repository;
        this.factoryStorage=factoryStorage;
        this.loginPresenter=new LoginPresenter(mainView.getILoginView(),this);
        this.editorPresenter = new EditorGameBookPresenter(mainView.getIEditorView(),this);
        this.innerGameBookPresenter= new InnerGameBookPresenter(mainView.getInnerGameBookView(),this);
        view.setPresenters(this);

    }

    /**
     *Set message in the MainView
     * @param message String message of the view
     */
    public void setMessage(String message){
        view.sendMessage(message);
    }

    /**
     * Send User to another view present in the MainView using an index (order of view)
     * @param index Integer index of the view
     */
    public void sendView(int index){
        view.goTo(index);
    }

    /**
     *  Set the gamebook to the InnerGameBookView to be completed with page and choice
     * @param isbn String isbn of the gameBook
     */
    public void completingGameBook(String isbn){
        innerGameBookPresenter.completeBook(isbn);
    }

    public FactoryStorage getFactoryStorage(){
        return factoryStorage;
    }
    public LibraryRepository getRepository(){
        return repository;
    }

    public IMainView getView() {
        return view;
    }

    public Users getUsers() {
        return users;
    }

    public Library getLibrary() {
        return library;
    }

    public Pages getPages() {
        return pages;
    }

    public Choices getChoices() {
        return choices;
    }
    public User getUser(){return user;}


    public void setUser(User user) {
        this.user=user;
        view.setUser(user.formatUser());
        editorPresenter.setGameBookList();

    }

    /**
     *  Asking the view to show an alert to the user to make a choice
     * @param s String question ask the user
     * @return If the user press the OK Button return true else return false
     */
    public boolean showConfirmation(String s) {
       return view.showConfirmation(s);
    }

    /**
     * Clear all the Collection charged
     */
    public void clearAll(){
        choices.clear();
        pages.clear();
        library.clear();
        users.clear();
    }

    public LoginPresenter getLoginPresenter() {
        return loginPresenter;
    }

    public EditorGameBookPresenter getEditorPresenter() {
        return editorPresenter;
    }

    public InnerGameBookPresenter getInnerGameBookPresenter() {
        return innerGameBookPresenter;
    }

}
