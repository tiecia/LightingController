package deviceConfigurator;

import deviceConfigurator.DeviceConfigurator;
import javafx.fxml.FXML;

import java.io.IOException;

public class TreeContextMenu {


    DeviceConfigurator parent;

    public void initData(DeviceConfigurator parent){
        this.parent = parent;
    }

    @FXML
    private void addDevice(){
        try {
            parent.addDevice();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
