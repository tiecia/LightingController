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
    private BorderPane borderLayout;
    @FXML
    private ListView<Zone> zoneList;
    private ZoneListContextMenu contextMenuController;

    private DeviceConfigurator devices;
    private Stage hardwareConfigurator;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        zoneList.getSelectionModel().selectedItemProperty().addListener(e -> zoneSelectionChanged());

        //Preload hardware configurator
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../deviceConfigurator/deviceConfigurator.fxml"));
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

        //Create and set zone context menus
        contextMenuController = new ZoneListContextMenu(this);
        zoneList.setContextMenu(contextMenuController.getMenu());

        //Set zone list context menu state
        if(zoneList.getSelectionModel().getSelectedItem() != null) {
            contextMenuController.deselect(false);
        } else {
            contextMenuController.deselect(true);
        }
    }

    private void zoneSelectionChanged() {
        if(zoneList.getSelectionModel().getSelectedItem() != null) {
            contextMenuController.deselect(false);
            borderLayout.setCenter(zoneList.getSelectionModel().getSelectedItem().getPanel());
            zoneList.getSelectionModel().getSelectedItem().setAnimations();
        } else {
            contextMenuController.deselect(true);
            borderLayout.setCenter(null);
        }
    }

    @FXML
    public void addZone(){
        CreateLightingZone newZoneDialog = new CreateLightingZone((Stage)zoneList.getScene().getWindow(), devices);
        //Wait for user
        if(newZoneDialog.getZone() != null){
            zoneList.getItems().add(newZoneDialog.getZone());
            zoneList.getSelectionModel().selectLast();
        }
    }

    @FXML
    public void editZone(){
        zoneList.getSelectionModel().getSelectedItem().edit();
    }

    @FXML
    public void removeZone(){
        ((Zone) zoneList.getItems().get(zoneList.getSelectionModel().getSelectedIndex())).close();
        zoneList.getItems().remove(zoneList.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void openDeviceConfig() throws IOException {
        hardwareConfigurator.showAndWait();
    }
}
