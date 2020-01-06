package deviceConfigurator.fcServerSettings.addFCBoard.choices;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class BoardOptionController {

    @FXML
    TextField ipField;
    @FXML
    TextField portField;

    public String getIP(){
        return ipField.getText();
    }

    public int getPort() {
        return Integer.parseInt(portField.getText());
    }
}
