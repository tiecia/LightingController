package lightingZone.ledZone;

import deviceConfigurator.fcServerSettings.fcBoard.FCStripSettings.FCStrip;
import devices.ControllableItem;
import devices.opc.Animation;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import lightingZone.ControlTab;
import lightingZone.ledZone.skills.Skill;
import lightingZone.ledZone.skills.chroma.Chroma;
import lightingZone.ledZone.skills.solidColor.SolidColorSkill;

import java.util.HashSet;

public class LedZone extends Tab implements ControlTab {

    @FXML
    private ChoiceBox<Skill> skillChooser;
    @FXML
    private BorderPane layout;

    private HashSet<ControllableItem> devices;

    private Parent panel;

    public void initData(HashSet<ControllableItem> devices){
        this.devices = devices;
        skillChooser.getItems().add(new SolidColorSkill());
        skillChooser.getItems().add(new Chroma(this));
        skillChooser.getSelectionModel().selectedItemProperty().addListener(e->{
            skillChooser.getSelectionModel().getSelectedItem().initData(this);
            layout.setCenter(skillChooser.getSelectionModel().getSelectedItem().getPanel());
            for(ControllableItem s : devices){
                ((FCStrip)s).getOPC().setAnimation((Animation)skillChooser.getSelectionModel().getSelectedItem());
                ((FCStrip)s).getOPC().animate();
            }
        });
    }

    public void setPanel(Parent panel){
        this.panel = panel;
        super.setContent(panel);
        super.setText("LED Strip");
    }

    public HashSet<ControllableItem> getDevices(){
        return devices;
    }
}
