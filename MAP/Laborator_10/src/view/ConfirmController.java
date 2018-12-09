package view;

import domain.Nota;
import domain.Student;
import domain.Tema;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import repository.ValidationException;
import utils.ChangeEventType;
import utils.NotaChangeEvent;

import java.util.Optional;

public class ConfirmController {

    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label descriere;

    private Stage stage;
    private Nota nota;
    private boolean motivat;
    private String feedback;
    private GradeController controller;

    public void init(Stage stage, Nota nota,boolean motivat,
            String feedback,GradeController controller){
        this.stage=stage;
        this.nota=nota;
        this.controller=controller;
        this.motivat=motivat;
        this.feedback=feedback;
        message();
    }

    private void message(){
        Student student=this.controller.service.cautaStudent(this.nota.getStudentID()).get();
        Tema tema=this.controller.service.cautaTema(this.nota.getTemaID()).get();
        this.descriere.setText("Studentul de notat: "+student.getNume()+" din grupa "+student.getGrupa()
                +"\nTema: Lab"+tema.getID()+" ("+tema.getDescriere()+") \n     deadline: "+tema.getDeadline()+"\n"
                +"Nota primita: "+this.controller.service.calculeazaNota(this.nota.getDataCurenta(),
                this.nota.getNotaProf().toString(),this.controller.service.cautaTema(nota.getTemaID()).get())
                );
    }

    @FXML
    public void handleCancelButton(){
        this.stage.close();
    }

    @FXML
    public void handleOKButton(){

        try {
            Optional<Nota> saved=this.controller.service.adaugaNota(nota.getStudentID(), nota.getTemaID(),
                    nota.getDataCurenta(),nota.getNotaProf().toString(),feedback,motivat);
            if (!saved.isPresent()) {
                this.controller.showMessage(Alert.AlertType.INFORMATION, "Adaugare informatii", "Nota a fost adaugata!");
                controller.update(new NotaChangeEvent(ChangeEventType.ADD,nota));
                controller.averageController.update(new NotaChangeEvent(ChangeEventType.ADD));
            }
            else
                this.controller.showErrorMessage("Studentul are deja nota!");
        } catch (ValidationException e) {
            this.controller.showErrorMessage("Date invalide!");
        }
        this.stage.close();
    }


}
