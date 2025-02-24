package deviceConfigurator.fcServerSettings.fcBoard.FCStripSettings;

import deviceConfigurator.fcServerSettings.fcBoard.FCBoard;
import devices.ControllableItem;
import devices.DeviceManager;
import devices.opc.Animation;
import devices.opc.PixelStrip;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import lightingZone.ledZone.LedZone;

import java.io.IOException;


public class FCStrip extends CheckBoxTreeItem<String> implements DeviceManager, ControllableItem {
    @FXML
    TextField nameField;
    @FXML
    TextField pinField;
    @FXML
    TextField numLedField;

    String name;
    int pin;
    int numLeds;

    //This manager's strip setting page;
    Parent settingsPage;

    //Link back to this strip's board manager
    FCBoard boardManager;

    //This manager's LED Strip
    PixelStrip strip;

    //Initialize this strip with its setting page and corresponding board manager
    public static LedZone getControlTab() {
        FXMLLoader loader = new FXMLLoader(FCStrip.class.getResource("../../../../lightingZone/ledZone/ledZone.fxml"));
        Parent panel = null;
        try {
            panel = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loader.<LedZone>getController().setPanel(panel);
        return loader.getController();
    }

    public void initData(Parent settingsPage, FCBoard boardManager) {

        this.settingsPage = settingsPage;
        this.boardManager = boardManager;
        this.strip = boardManager.getOPC().addPixelStrip(0,0);
        pinField.setText("0");
        numLedField.setText("0");
        super.setValue(toString());
    }

    public void removeThisStrip(){
        boardManager.removeStrip(this);
    }

    public PixelStrip getOPC(){
        return strip;
    }

    public String toString() {
        if (name == null || name.equals("")) {
            return "LED Strip";
        }
        return name;
    }

    public Parent getSettingPage() {
        return settingsPage;
    }

    public ContextMenu getContextMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("stripContextMenu.fxml"));
        ContextMenu menu = null;
        try {
             menu = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((StripContextMenu)loader.getController()).initData(this);
        return menu;
    }

    public FCBoard getParentFCManager(){
        return boardManager;
    }

    @FXML
    private void nameChanged(){
        this.name = name;
        super.setValue(toString());
    }

    @FXML
    /**
         Invoked whenever the pin or num fields are changed.

         Gets the data. Removes the previous strip from its corresponding OpcDevice.
         Adds a new strip to the OpcDevice with new data
     */
    private void dataChanged(){
        String prevPinContent = pinField.getText();
        String prevNumContent = numLedField.getText();
        int pin = 0;
        int num = 0;
        try {
            System.out.println("PinFieldContent: " + pinField.getText());
            System.out.println("NumFieldContent: " + numLedField.getText());
            pin = Integer.parseInt(pinField.getText());
            num = Integer.parseInt(numLedField.getText());
            System.out.println("Changed");
            Animation prevAnimation = strip.getAnimation();
            boardManager.getOPC().removeStrip(strip);
            PixelStrip newStrip = boardManager.getOPC().addPixelStrip(pin, num);
            newStrip.setAnimation(prevAnimation);
            System.out.println(newStrip);
            this.strip = newStrip;
            this.pin = pin;
            this.numLeds = num;
        } catch (NumberFormatException e){
            System.out.println("Invalid Input");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Pin must be between 0 and 7");
        }
    }
}