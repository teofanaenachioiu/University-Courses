package noteReports;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.NotaDTO;


import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NoteController {

    ObservableList<NotaDTO> model=FXCollections.observableArrayList();


    @FXML
    TableColumn<NotaDTO,String> tableColumnName;
    @FXML
    TableColumn<NotaDTO,String> tableColumnTema;
    @FXML
    TableColumn<NotaDTO,Double> tableColumnNota;
    @FXML
    TableView<NotaDTO> tableViewNote;
    //----------------------end TableView fx:id----------------

    @FXML
    TextField textFieldName;
    @FXML
    TextField textFieldTema;
    @FXML
    TextField textFieldNota;

    @FXML
    public void initialize()
    {
        tableColumnName.setCellValueFactory(new PropertyValueFactory<NotaDTO, String>("studentName"));
        tableColumnTema.setCellValueFactory(new PropertyValueFactory<NotaDTO, String>("temaId"));
        tableColumnNota.setCellValueFactory(new PropertyValueFactory<NotaDTO, Double>("nota"));

        tableViewNote.setItems(model);
        model.setAll(getNotaDTOList());

        textFieldName.textProperty()
                .addListener(o->handleFilter());

        textFieldTema.textProperty()
            .addListener(o->handleFilter());

        textFieldNota.textProperty()
                .addListener(o->handleFilter());
    }

    private List<NotaDTO> getNotaDTOList(){
        return RepoNote.findAll()
                .stream()
                .map((nota)->{
                    String numeStudent = nota.getStudent().getName();
                    String idTema = nota.getTema().getId();
                    double notaValue = nota.getValue();
                    return new NotaDTO(numeStudent, idTema, notaValue);
                })
                .collect(Collectors.toList());

    }

    private void handleFilter() {
        Predicate<NotaDTO> p1 = n->n.getStudentName()
                .contains(textFieldName.getText());
        Predicate<NotaDTO> p2 = n->n.getTemaId()
                .contains(textFieldTema.getText());
        Predicate<NotaDTO> p3 = n ->
        {
            try {
                return Math
                        .round(n.getNota()) >= Long.parseLong(textFieldNota.getText());
            } catch (NumberFormatException e) {
                return true;
            }
        };


        model.setAll(getNotaDTOList()
                .stream()
                .filter(p1.and(p2).and(p3))
                .collect(Collectors.toList()));



    }


}
