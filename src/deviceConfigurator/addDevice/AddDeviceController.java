package deviceConfigurator.addDevice;

import deviceConfigurator.addDevice.choices.ArduinoOptionController;
import deviceConfigurator.addDevice.choices.FCOptionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddDeviceController implements Initializable {
    @FXML
    BorderPane borderPane;
    @FXML
    ChoiceBox<String> chooser;
    @FXML
    Button okButton;
    @FXML
    Button cancelButton;
    @FXML
    Pane infoPane;

    String selection;

    FCOptionController fcController;
    ArduinoOptionController arduinoController;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        chooser.getItems().addAll("Fadecandy Server", "Arduino");
        chooser.setValue("Fadecandy Server");
        setFC();
        chooser.getSelectionModel().selectedIndexProperty().addListener(e -> {
            deviceChanged();
        });
    }

    private void deviceChanged() {
        if(chooser.getValue() != null) {
            if (chooser.getValue().equals("Arduino")) { //For some reason this has to be opposite of what is being set
                setFC();
            } else if (chooser.getValue().equals("Fadecandy Server")) { //For some reason this has to be opposite of what is being set
                setArduino();
            }
        }
    }

    private void setFC(){
        FXMLLoader settingPageLoader = new FXMLLoader(getClass().getResource("choices/fcOption.fxml"));
        Parent settingPanel = null;
        try {
            settingPanel = settingPageLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fcController = settingPageLoader.getController();
        infoPane.getChildren().clear();
        infoPane.getChildren().add(settingPanel);
    }

    private void setArduino(){
        FXMLLoader settingPageLoader = new FXMLLoader(getClass().getResource("choices/arduinoOption.fxml"));
        Parent settingPanel = null;
        try {
            settingPanel = settingPageLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        arduinoController = settingPageLoader.getController();
        infoPane.getChildren().clear();
        infoPane.getChildren().add(settingPanel);
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

    public String getServerIP(){
        return fcController.getIP();
    }

    public int getServerPort(){
        return fcController.getPort();
    }

    public String getArduinoPort(){
        return "";
    }
}
