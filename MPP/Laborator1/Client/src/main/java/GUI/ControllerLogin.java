package GUI;

import GUI.operator.ControllerOperator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.TipUser;
import model.User;
import service.ServiceAdmin;
import service.ServiceOperator;

import java.io.IOException;

public class ControllerLogin {
    @FXML
    TextField fieldUser;
    @FXML
    PasswordField fieldPassword;
    @FXML
    Label errorLabel;

    ServiceAdmin serviceAdmin;
    ServiceOperator serviceOperator;

    private Stage primaryStage;
    private Scene operatorScene;
    //private Scene adminScene;
    private Scene loginScene;
    //private ControllerAdmin adminController;
    private ControllerOperator controllerOperator;

    private void initViewOperator(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewOperator.fxml"));
        AnchorPane operatorLayout = loader.load();
        Scene sceneOperator = new Scene(operatorLayout,600,400);

        this.controllerOperator=loader.getController();

        controllerOperator.setLoginScene(loginScene);
        controllerOperator.setPrimaryStage(primaryStage);
        controllerOperator.setData(serviceOperator,user);
        this.operatorScene = sceneOperator;
    }

    public void init(ServiceOperator serviceOperator, ServiceAdmin serviceAdmin,Scene loginScene){
        this.serviceAdmin=serviceAdmin;
        this.serviceOperator=serviceOperator;
        this.loginScene=loginScene;
        this.errorLabel.setVisible(false);
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage=primaryStage;
    }
    @FXML
    public void handleLogin() throws IOException {
        String username = "";
        String password = "";

        errorLabel.setVisible(false);

        if (fieldUser.getText() != null)
            username = fieldUser.getText().trim();
        if (fieldPassword.getText() != null)
            password = fieldPassword.getText();

        boolean valid=serviceAdmin.verificareParola(username, password);
        if (valid) {
            User utilizator=serviceAdmin.cauta(username);
            if(utilizator.getTip().equals(TipUser.ADMIN)){
                System.out.println("AM INTRAT LA ADMIN");
//                initViewAdmin();
//                handleAdminWindow();
            }
            else if(utilizator.getTip().equals(TipUser.OPERATOR)) {
                initViewOperator(utilizator);
                handleOperatorWindow();
            }

        } else {
            errorLabel.setVisible(true);
        }
        fieldPassword.clear();
        fieldUser.setText(null);
        fieldUser.requestFocus();

    }

    private void handleOperatorWindow()
    {
        this.primaryStage.setScene(this.operatorScene);
    }

}
