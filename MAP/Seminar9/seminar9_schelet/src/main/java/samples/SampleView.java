package samples;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public class SampleView {

    public static HBox getView(){
        HBox hb=new HBox();

//        HashSet<String> hashSet=new HashSet<>();
//        hashSet.add("ana");
//        hashSet.add("anuta");
//        hashSet.add("aniela");
//        hashSet.add("dana");
//
//        hb.getChildren().add(new AutoCompleteTextField(hashSet,1)
//        );

        final ComboBox emailComboBox = new ComboBox();
        emailComboBox.getItems().addAll(
                "jacob.smith@example.com",
                "isabella.johnson@example.com",
                "ethan.williams@example.com",
                "emma.jones@example.com",
                "michael.brown@example.com"
        );
        hb.getChildren().add(emailComboBox);
        new AutoCompleteComboBoxListener<String>(emailComboBox);
        return hb;
    }
}
