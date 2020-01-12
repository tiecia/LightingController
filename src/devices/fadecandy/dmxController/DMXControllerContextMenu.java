package devices.fadecandy.dmxController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;

import java.io.IOException;

public class DMXControllerContextMenu{

    private DMXController controlingDevice;

    private ContextMenu menu;

    public static ContextMenu newDMXControllerContextMenu(DMXController controller){
        return new DMXControllerContextMenu(controller).getMenu();
    }

    public DMXControllerContextMenu(DMXController controlingDevice){
        this.controlingDevice = controlingDevice;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dmxControllerContextMenu.fxml"));
        loader.setController(this);
        try {
            menu = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addDevice(){
        controlingDevice.addDevice();
    }

    @FXML
    public void removeThisController(){
        controlingDevice.removeThisDevice();
    }

    public ContextMenu getMenu(){
        return menu;
    }
}
