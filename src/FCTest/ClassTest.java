package FCTest;

import devices.DeviceManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class ClassTest extends Application {
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scanner s = new Scanner(System.in);
        System.out.println(s.getClass() == Scanner.class);
        FXMLLoader loader = new FXMLLoader(ClassTest.class.getResource("../devices/fadecandy/fcServer/fcServerSettings.fxml"));
        Parent p = loader.load();
        DeviceManager controller = loader.getController();
        System.out.println(controller.getClass());
        CheckBoxTreeItem<String> item = (CheckBoxTreeItem<String>)controller;
        System.out.println(item.getClass());
    }
}
