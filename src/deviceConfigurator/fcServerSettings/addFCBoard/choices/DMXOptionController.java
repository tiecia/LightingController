package deviceConfigurator.fcServerSettings.addFCBoard.choices;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DMXOptionController {

    @FXML
    TextField portField;

    public String getPort(){
        return portField.getText();
    }
}
