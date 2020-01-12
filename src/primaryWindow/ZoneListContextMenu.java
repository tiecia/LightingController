package primaryWindow;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import lightingZone.CreateLightingZone;
import lightingZone.Zone;

import java.io.IOException;

public class ZoneListContextMenu {

    private ContextMenu menu;
    private PrimaryWindowController controlWindow;

    private @FXML MenuItem editZone;
    private @FXML MenuItem removeZone;

    public ZoneListContextMenu(PrimaryWindowController controlWindow){
        this.controlWindow = controlWindow;
        FXMLLoader contextLoader = new FXMLLoader(getClass().getResource("zoneListContextMenu.fxml"));
        contextLoader.setController(this);
        try {
            this.menu = contextLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ContextMenu getMenu() {
        return menu;
    }

    public void deselect(boolean d){
        editZone.setDisable(d);
        removeZone.setDisable(d);
    }

    @FXML
    private void addZone(){
        controlWindow.addZone();
    }

    @FXML
    private void editZone(){
        controlWindow.editZone();
    }

    @FXML
    private void removeZone(){
        controlWindow.removeZone();
    }
}
