package primaryWindow;

import deviceConfigurator.DeviceConfigurator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lightingZone.CreateLightingZone;
import lightingZone.Zone;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PrimaryWindowController implements Initializable {

    @FXML
    BorderPane borderLayout;
    @FXML
    ListView<Zone> zoneList;

    DeviceConfigurator devices;

    Stage hardwareConfigurator;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        zoneList.getSelectionModel().selectedItemProperty().addListener(e -> displayNewZone());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../deviceConfigurator/DeviceConfigurator.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        devices = loader.getController();
        Scene parentScene = new Scene(parent);
        this.hardwareConfigurator = new Stage();
        hardwareConfigurator.setScene(parentScene);
        hardwareConfigurator.initModality(Modality.APPLICATION_MODAL);
    }

    private void displayNewZone() {
        borderLayout.setCenter(zoneList.getSelectionModel().getSelectedItem().getPanel());
    }

    @FXML
    private void addZone(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../lightingZone/createLightingZone.fxml"));
        Parent content = null;
        try {
            content = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CreateLightingZone controller = loader.getController();
        controller.initData(devices);
        Scene scene = new Scene(content);
        Stage window = new Stage();
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
        //Wait for user

        if(controller.getZone() != null){
            zoneList.getItems().add(controller.getZone());
        }
    }

    @FXML
    private void openDeviceConfig() throws IOException {
        hardwareConfigurator.showAndWait();
    }
}
