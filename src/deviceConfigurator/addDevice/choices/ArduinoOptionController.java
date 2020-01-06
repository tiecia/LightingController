package deviceConfigurator.addDevice.choices;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ArduinoOptionController {

    @FXML
    TextField portField;

    public String getPort(){
        return portField.getText();
    }
}
