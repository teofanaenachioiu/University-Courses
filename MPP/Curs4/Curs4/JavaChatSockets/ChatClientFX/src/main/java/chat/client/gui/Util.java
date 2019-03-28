package chat.client.gui;

import javafx.scene.control.Alert;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util {

    private static Logger logger = LogManager.getLogger(Util.class.getName());

    public static void showWarning(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("MPP chat");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();

    }


    }

