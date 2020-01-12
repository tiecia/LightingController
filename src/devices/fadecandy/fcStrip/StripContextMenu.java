package devices.fadecandy.fcStrip;

import javafx.fxml.FXML;

public class StripContextMenu {

    private FCStrip strip;

    public void initData(FCStrip strip){
        this.strip = strip;
    }

    @FXML
    private void addLedStrip(){
        strip.getParentFCManager().addLedStrip();
    }

    @FXML
    private void addDmx(){
        //@TODO addDMX
    }

    @FXML
    private void removeLedStrip(){
        strip.removeThisStrip();
    }
}
