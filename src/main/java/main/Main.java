package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.IOException;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Main extends Application {
    private static Stage primaryStage;
    private static BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Optic");

        initRootLayout();

        showYoungOverview();
    }

    private static void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showYoungOverview(){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/YoungOverview.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            rootLayout.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showMichelsonOverview(){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/MichelsonOverview.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            rootLayout.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void showNewtonOverview(){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/NewtonOverview.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            rootLayout.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
