package GUI.admin;

import GUI.operator.ControllerInscrieri;
import GUI.operator.ControllerProbe;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Categorie;
import model.Participant;
import model.TipUser;
import model.User;
import model.dto.ProbaDTO;
import services.IObserver;
import services.IServer;
import services.MyAppException;
import utils.AutoCompleteComboBoxListener;
import utils.GUIutils;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerAdmin extends UnicastRemoteObject implements Initializable, IObserver {
    @FXML
    AnchorPane mainPane;
    @FXML
    AnchorPane viewPane;
    @FXML
    Button utilizatoriButton;
    @FXML
    Button logoutButton;
    @FXML
    Button addButton;
    @FXML
    Button deleteButton;
    @FXML
    Button updateButton;
    @FXML
    Label usernameLabel;
    @FXML
    TableView<User> tabelView;
    @FXML
    TableColumn<User, String> columnUsername;
    @FXML
    TableColumn<User, String> columnTip;
    @FXML
    TextField textFieldUsername;
    @FXML
    TextField textFieldParola;
    @FXML
    ComboBox<TipUser> comboBoxTip;

    private IServer server;
    private User user;

    private Stage primaryStage;
    private Scene loginScene;
    private final ObservableList<User> model = FXCollections.observableArrayList();

    public ControllerAdmin() throws RemoteException {
    }

    public void setData(IServer server, User user) throws IOException {

        setUser(user);
        setServer(server);
        User[] probaDTO = this.server.listaUseri();
        ArrayList<User> items = new ArrayList<>(Arrays.asList(probaDTO));
        model.setAll(items);

        tabelView.getSelectionModel().selectedItemProperty().addListener((ov, t, t1) -> {
            User user1 = tabelView.getSelectionModel().getSelectedItem();
            if (user1 != null) {
                textFieldUsername.setText(user1.getID());
                comboBoxTip.getSelectionModel().select(user1.getTip());
            }

        });
    }

    private void clearFields() {
        this.textFieldParola.setText(null);
        this.textFieldUsername.setText(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnUsername.setCellValueFactory(cellData -> {
            User current_item = cellData.getValue();
            return new ReadOnlyStringWrapper("" + current_item.getID());
        });
        columnTip.setCellValueFactory(new PropertyValueFactory<>("tip"));
        tabelView.setItems(model);
        ObservableList<TipUser> combo = FXCollections.observableArrayList();
        List<TipUser> tipuri = new ArrayList<>();
        tipuri.add(TipUser.ADMIN);
        tipuri.add(TipUser.OPERATOR);
        combo.setAll(tipuri);
        comboBoxTip.setItems(combo);


    }

    private void setUser(User user) {
        this.user = user;
        usernameLabel.setText("ADMIN " + this.user.getID());
    }

    private void setServer(IServer server) throws IOException {
        this.server = server;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setLoginScene(Scene scene) {
        this.loginScene = scene;
    }

    @FXML
    public void handleAdd() {
        String username = textFieldUsername.getText();
        String parola = textFieldParola.getText();
        TipUser tip = comboBoxTip.getValue();
        if (username == null || username.equals("") || parola == null || parola.equals("") || tip == null) {
            GUIutils.showErrorMessage("Date invalide");
        } else {
            try {
                server.addUser(username, parola, tip);
                clearFields();
            } catch (MyAppException e) {
                GUIutils.showErrorMessage(e.getMessage());
            }

        }
    }

    @FXML
    public void handleUpdate() {
        String username = textFieldUsername.getText();
        String parola = textFieldParola.getText();
        TipUser tip = comboBoxTip.getValue();
        User user = tabelView.getSelectionModel().getSelectedItem();
        if (user == null)
            GUIutils.showErrorMessage("Selectati username-ul");
        else {
            try {
                server.updateUser(new User(username, parola, tip));
                clearFields();
            } catch (MyAppException e) {
                //e.printStackTrace();
            }
        }

    }

    @FXML
    public void handleDelete() {
        User user = tabelView.getSelectionModel().getSelectedItem();
        if (user != null) {
            try {
                server.deleteUser(user.getID());
                clearFields();
            } catch (MyAppException e) {
                GUIutils.showErrorMessage(e.getMessage());
            }
        } else {
            GUIutils.showErrorMessage("Selectati un user");
        }
    }

    @FXML
    public void handleLogout() {
        System.out.println("AM IESIT DE LA ADMIN " + user.getID());
        try {
            this.server.logout(user);
        } catch (MyAppException e) {
            System.out.println("Am prins eroarea: " + e.getMessage());
        }
        this.primaryStage.setScene(loginScene);
    }

    @Override
    public void update() throws MyAppException {
        System.out.println("Incerc sa updatez userii");
        User[] usersDTO = this.server.listaUseri();
        ArrayList<User> items = new ArrayList<>(Arrays.asList(usersDTO));
        model.setAll(items);

    }
}
