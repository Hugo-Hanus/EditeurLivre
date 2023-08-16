/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.helmo.gbeditor;


import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.helmo.gbeditor.infrastructure.storage.storage.MySQLConnection;
import org.helmo.gbeditor.infrastructure.storage.storage.FactoryStorage;

import org.helmo.gbeditor.presenters.MainPresenter;

import org.helmo.gbeditor.infrastructure.mapper.MapperOne;
import org.helmo.gbeditor.infrastructure.mapper.UniversalMapper;
import org.helmo.gbeditor.infrastructure.repository.JSONLibraryRepository;
import org.helmo.gbeditor.repository.LibraryRepository;
import org.helmo.gbeditor.storage.*;

import org.helmo.gbeditor.views.MainView;


import java.nio.file.Path;

/***
 * Main Program
 */
public class App extends Application {
    private static final Path PATH = Path.of((System.getProperty("user.home")));

    /**
     * Main Launcher
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start The view
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     */
    @Override
    public void start(Stage primaryStage) {
        MainView mainView = new MainView();
        UniversalMapper mapper = new MapperOne();
        ConnectionData mysql = new MySQLConnection();
        FactoryStorage factoryStorage = new FactoryStorage(mysql);
        LibraryRepository repository = new JSONLibraryRepository(mapper, PATH);
        MainPresenter mainPresenter = new MainPresenter(mainView, repository, factoryStorage);
        Parent root = mainView.getRoot();
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setTitle("GameBook - Editing");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
