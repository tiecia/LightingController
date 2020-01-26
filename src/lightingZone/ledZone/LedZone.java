package lightingZone.ledZone;

import devices.fadecandy.fcStrip.FCStrip;
import devices.ControllableItem;
import devices.fadecandy.opc.Animation;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
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
    private @FXML ScrollPane scrollPane;

    private HashSet<ControllableItem> devices;

    private Parent panel;

    public void initData(HashSet<ControllableItem> devices){
        this.devices = devices;

        //Add the different skills to the skill chooser
        skillChooser.getItems().add(new SolidColorSkill());
        skillChooser.getItems().add(new Chroma(this));
        skillChooser.getSelectionModel().selectedItemProperty().addListener(e->{
            //Skill Changed
            skillChooser.getSelectionModel().getSelectedItem().initData(this);
            scrollPane.setContent(skillChooser.getSelectionModel().getSelectedItem().getPanel());
            for(ControllableItem s : devices){
                ((FCStrip)s).getOPC().setAnimation((Animation)skillChooser.getSelectionModel().getSelectedItem());
            }
        });
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
    }

    @Override
    public void close() {
        for(ControllableItem ci : devices){
            ci.resetThread();
        }
    }

    @Override
    public void setAnimation() {
        for(ControllableItem c: devices) {
            ((FCStrip) c).getOPC().setAnimation(((Animation) skillChooser.getSelectionModel().getSelectedItem()));
        }
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
