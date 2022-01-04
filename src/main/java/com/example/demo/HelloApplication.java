package com.example.demo;
import org.sqlite.JDBC;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.sql.*;

import static java.sql.DriverManager.getConnection;


public class HelloApplication extends Application {

    public final String Connection_String = "jdbc:sqlite::memory:";



    @Override
    public void start(Stage stage) throws IOException {



        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ScatterPlot");
        stage.setScene(scene);
//        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        String css = Objects.requireNonNull(this.getClass().getResource("application.css")).toExternalForm();
        scene.getStylesheets().add(css);
//        stage.sizeToScene();
//        stage.setFullScreen(true);
        stage.setMaximized(true);



        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}