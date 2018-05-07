package controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioMenuItem;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import main.Main;

import java.awt.event.ActionEvent;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class RootLayoutController {
    @FXML
    private RadioMenuItem radioYoung;

    @FXML
    private RadioMenuItem radioNewton;

    @FXML
    private RadioMenuItem radioMichelson;

    @FXML
    void initialize() {
        radioYoung.setSelected(true);
    }

    @FXML
    private void selectMichelson() {
        radioNewton.setSelected(false);
        radioYoung.setSelected(false);
        Main.showMichelsonOverview();
    }

    @FXML
    private void selectNewton() {
        radioYoung.setSelected(false);
        radioMichelson.setSelected(false);
        Main.showNewtonOverview();
    }

    @FXML
    private void selectYoung() {
        radioNewton.setSelected(false);
        radioMichelson.setSelected(false);
        Main.showYoungOverview();
    }
}
