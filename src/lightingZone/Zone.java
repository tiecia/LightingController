package lightingZone;

import deviceConfigurator.fcServerSettings.fcBoard.FCStripSettings.FCStrip;
import devices.ControllableItem;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import lightingZone.ledZone.LedZone;

import java.util.*;

public class Zone {

    @FXML
    private TabPane tabbedPane;

    @FXML
    private Parent panel;


    public void initData(TreeView<String> tree, Parent panel){
        this.panel = panel;
        HashSet<CheckBoxTreeItem<String>> selectedItems = new HashSet<>();
        findCheckedItems((CheckBoxTreeItem<String>) tree.getRoot(), selectedItems);
        System.out.println(selectedItems);
        HashMap<LedZone, HashSet<ControllableItem>> tabs = new HashMap<>();

        //Add FCStrip Tab
        LedZone FCStripTab = FCStrip.getControlTab();
        tabs.put(FCStripTab, new HashSet<>());

        for(CheckBoxTreeItem<String> s : selectedItems){  //Populates the map
            Class c = s.getClass();
            if(c == FCStrip.class){
                tabs.get(FCStripTab).add((FCStrip)s);
            }
        }
        for(LedZone t : tabs.keySet()){
            tabbedPane.getTabs().add(t);
            t.initData(tabs.get(t));
        }
        tabbedPane.getTabs().remove(0);
        tabbedPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

    public Parent getPanel(){
        return panel;
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
