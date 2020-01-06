package deviceConfigurator.fcServerSettings.addFCBoard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class AddFCBoardController implements Initializable {
    @FXML
    BorderPane borderPane;
    @FXML
    ChoiceBox<String> chooser;
    @FXML
    Button okButton;
    @FXML
    Button cancelButton;

    String selection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chooser.getItems().addAll("Fadecandy Board", "DMX Device");
        chooser.setValue("Fadecandy Board");
    }

    @FXML
    private void okButtonClicked() {
        selection = chooser.getValue();
        close();
    }

    @FXML
    private void cancelButtonClicked(){
        close();
    }

    private void close(){
        Stage thisWindow = (Stage) chooser.getScene().getWindow();
        thisWindow.close();
    }

    public String getSelection(){
        return this.selection;
    }
}
