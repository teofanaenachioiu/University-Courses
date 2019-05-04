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
import org.apache.thrift.TException;
import org.teofana.concurs.ConcursService;
import org.teofana.concurs.MyAppExecption;

import java.io.IOException;

public class ControllerLogin {
    @FXML
    TextField fieldUser;
    @FXML
    PasswordField fieldPassword;
    @FXML
    Label errorLabel;

    private ConcursService.Client client;

    private Stage primaryStage;
    private Scene operatorScene;
    private Scene loginScene;

    private void initViewOperator(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewOperator.fxml"));
        AnchorPane operatorLayout = loader.load();
        Scene sceneOperator = new Scene(operatorLayout,600,400);

        ControllerOperator controllerOperator = loader.getController();

        controllerOperator.setLoginScene(loginScene);
        controllerOperator.setPrimaryStage(primaryStage);
        controllerOperator.setData(client,user);
        this.operatorScene = sceneOperator;
    }

    public void init(ConcursService.Client client,Scene loginScene){
        this.client=client;
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

        try{
            client.login(username, password);
            User utilizator=client.cauta(username);
            if (utilizator != null){
                if(utilizator.getTipUser().equals(TipUser.ADMIN)){
                    System.out.println("AM INTRAT LA ADMIN");
                }
                else if(utilizator.getTipUser().equals(TipUser.OPERATOR)) {
                    System.out.println("AM INTRAT LA OPERATOR");
                    initViewOperator(utilizator);
                    handleOperatorWindow();
                }
            }
        }
        catch (MyAppExecption e){
            System.out.println("Am prins eroarea: "+ e.getMessage());
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        } catch (TException e) {
            e.printStackTrace();
        }
        fieldPassword.clear();
        fieldUser.setText(null);
        fieldUser.requestFocus();

    }

    private void handleOperatorWindow()
    {
        this.primaryStage.setScene(this.operatorScene);
    }

//    @Override
//    public void update() throws MyAppException {
//        System.out.println("Update la login");
//        controllerOperator.update();
//    }
}
