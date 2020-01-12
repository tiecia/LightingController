package lightingZone;

import devices.fadecandy.fcStrip.FCStrip;
import devices.ControllableItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import lightingZone.ledZone.LedZone;

import java.io.IOException;
import java.util.*;

public class Zone {

    @FXML
    private TabPane tabbedPane;

    @FXML
    private Parent panel;

    private String name;
    private CreateLightingZone options;

    public Zone(CreateLightingZone createDialog, String name){
        //Set Fields
        this.options = createDialog;
        this.name = name;

        //Load this this Zone's FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("zone.fxml"));
        loader.setController(this);
        try {
            this.panel = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        update();

        tabbedPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

    public Parent getPanel(){
        return panel;
    }

    public void edit(){
        options.showAndWait();
        //Wait for user
        update();
    }

    public void close(){
        for(Tab t : tabbedPane.getTabs()){
            ControlTab ct = ((ControlTab) t);
            ct.close();
        }
    }

    @Override
    public String toString() {
        return name;
    }

    private void update(){
        //Update Zone Name
        this.name = options.getName();
        //Populate a Set of all items this zone controls
        HashSet<CheckBoxTreeItem<String>> selectedItems = new HashSet<>();
        findCheckedItems((CheckBoxTreeItem<String>) options.getTree().getRoot(), selectedItems);

        //Map relates each ControllableDevice to their respective UI
        HashMap<LedZone, HashSet<ControllableItem>> tabs = new HashMap<>();
        //Add FCStrip Tab
        LedZone FCStripTab = FCStrip.getControlTab();
        tabs.put(FCStripTab, new HashSet<>());

        //Fills relates each controllable device to its respective control UI. Stores in the map
        for(CheckBoxTreeItem<String> s : selectedItems){
            Class c = s.getClass();
            if(c == FCStrip.class){
                tabs.get(FCStripTab).add((FCStrip)s);
            }
        }

        tabbedPane.getTabs().clear();
        //Fills the tabbed pane with all required control UIs
        for(LedZone t : tabs.keySet()){
//            if(!tabs.get(t).isEmpty()) {
            tabbedPane.getTabs().add(t);
            t.initData(tabs.get(t));
//            }
        }
    }

    private void findCheckedItems(CheckBoxTreeItem<String> item, HashSet<CheckBoxTreeItem<String>> selectedItems) {
        if(item.isSelected() && item.isLeaf()) {
            selectedItems.add(item);
        } else {
            for (TreeItem<String> child : item.getChildren()) {
                findCheckedItems((CheckBoxTreeItem<String>) child, selectedItems);
            }
        }
    }
}
