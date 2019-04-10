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
import services.IObserver;
import services.IServer;
import services.MyAppException;

import java.io.IOException;

public class ControllerLogin implements IObserver {
    @FXML
    TextField fieldUser;
    @FXML
    PasswordField fieldPassword;
    @FXML
    Label errorLabel;

    IServer server;

    private Stage primaryStage;
    private Scene operatorScene;
    private Scene loginScene;
    private ControllerOperator controllerOperator;

    private void initViewOperator(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewOperator.fxml"));
        AnchorPane operatorLayout = loader.load();
        Scene sceneOperator = new Scene(operatorLayout,600,400);

        this.controllerOperator=loader.getController();

        controllerOperator.setLoginScene(loginScene);
        controllerOperator.setPrimaryStage(primaryStage);
        controllerOperator.setData(server,user);
        this.operatorScene = sceneOperator;
    }

    public void init(IServer server,Scene loginScene){
        this.server=server;
        this.loginScene=loginScene;
        this.errorLabel.setVisible(false);
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage=primaryStage;
    }
    @FXML
    public void handleLogin() throws IOException, MyAppException {
        String username = "";
        String password = "";

        errorLabel.setVisible(false);

        if (fieldUser.getText() != null)
            username = fieldUser.getText().trim();
        if (fieldPassword.getText() != null)
            password = fieldPassword.getText();

        try{
            server.login(username, password, this);
            User utilizator=server.cauta(username);
            if (utilizator != null){
                if(utilizator.getTip().equals(TipUser.ADMIN)){
                    System.out.println("AM INTRAT LA ADMIN");
                }
                else if(utilizator.getTip().equals(TipUser.OPERATOR)) {
                    System.out.println("AM INTRAT LA OPERATOR");
                    initViewOperator(utilizator);
                    handleOperatorWindow();
                }
            }
        }
        catch (MyAppException e){
            System.out.println("Am prins eroarea: "+ e.getMessage());
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

    @Override
    public void update() throws MyAppException {
        System.out.println("Update la login");
        controllerOperator.update();
    }
}
