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
    Stage primaryStage;
    BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Optic");

        /*
        Parent root = FXMLLoader.load(getClass().getResource("/view/RootLayout.fxml"));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        */

        initRootLayout();

        showOpticOverview();
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showOpticOverview(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OpticOverview.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            rootLayout.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void testCanvas() {

    }
}
