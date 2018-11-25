package sample;//file: Lister.java
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class SearchBoxSample {

    public SearchBoxSample() {
        String searchBoxCss = SearchBoxSample.class.getResource("SearchBox.css").toExternalForm();
        VBox vbox = VBoxBuilder.create().build();
        vbox.getStylesheets().add(searchBoxCss);
        vbox.setPrefWidth(200);
        vbox.setMaxWidth(Control.USE_PREF_SIZE);
        vbox.getChildren().add(new SearchBox());
    }

    private static class SearchBox extends Region {

        private javafx.scene.control.TextField textBox;
        private javafx.scene.control.Button clearButton;

        public SearchBox() {
            setId("SearchBox");
            getStyleClass().add("search-box");
            setMinHeight(24);
            setPrefSize(200, 24);
            setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
            textBox = new javafx.scene.control.TextField();
            textBox.setPromptText("Search");
            clearButton = new Button();
            clearButton.setVisible(false);
            getChildren().addAll(textBox, clearButton);
            clearButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent actionEvent) {
                    textBox.setText("");
                    textBox.requestFocus();
                }
            });
            textBox.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    clearButton.setVisible(textBox.getText().length() != 0);
                }
            });
        }

        @Override
        protected void layoutChildren() {
            textBox.resize(getWidth(), getHeight());
            clearButton.resizeRelocate(getWidth() - 18, 6, 12, 13);
        }
    }


}
public class Lister {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Lister v1.0");

        // create a combo box
        String [] items = { "uno", "due", "tre", "quattro", "cinque",
                "sei", "sette", "otto", "nove", "deici",
                "undici", "dodici" };
        JComboBox comboBox = new JComboBox(items);
        comboBox.setEditable(true);

        // create a list with the same data model
        final JList list = new JList(comboBox.getModel());

        // create a button; when it's pressed, print out
        // the selection in the list
        JButton button = new JButton("Per favore");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Object[] selection = list.getSelectedValues();
                System.out.println("-----");
                for ( Object o : selection )
                    System.out.println( o );
            }
        });

        // put the controls the content pane
        Container c = frame.getContentPane();  // unnecessary  in 5.0+
        JPanel comboPanel = new JPanel();
        comboPanel.add(comboBox);

        c.add(comboPanel, BorderLayout.NORTH);
        c.add(new JScrollPane(list), BorderLayout.CENTER);
        c.add(button, BorderLayout.SOUTH);

        frame.setSize(200, 200);
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setVisible(true);
    }
}