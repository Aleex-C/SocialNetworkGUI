package com.example.socialnetworkgui;

import com.example.socialnetworkgui.domain.Utilizator;
import com.example.socialnetworkgui.domain.validators.ValidationException;
import com.example.socialnetworkgui.service.MesajeService;
import com.example.socialnetworkgui.service.PrietenieService;
import com.example.socialnetworkgui.service.UtilizatorService;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.util.Objects;


public class startController extends Application {
    public static UtilizatorService utilizatorService;
    public static PrietenieService prietenieService;
    public static MesajeService mesajeService;
    @FXML
    Button RegisterButton;
    @FXML
    Button LoginButton;
    @FXML
    TextField Email;
    @FXML
    PasswordField Password;
    @FXML
    public void onLoginButtonClick() throws IOException{
        String email = Email.getText();
        String password = Password.getText();
        Email.clear();
        Password.clear();
        try{
            Utilizator thisUser = startController.utilizatorService.checkPassword(email, password);
            //Notifications.create().title("Successfully logged in!").hideAfter(Duration.seconds(3));
            Stage thisStage = (Stage) LoginButton.getScene().getWindow();
            MenuController.current_user=thisUser;
            thisStage.hide();
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("login.fxml"));
            Stage login_stage = new Stage();
            Scene scene = new Scene(loader.load());

            login_stage.setScene(scene);
            login_stage.show();
            login_stage.setOnCloseRequest(e->thisStage.show());
            login_stage.setOnHidden(e->thisStage.show());
        } catch (ValidationException ex){
            Notifications.create().title("Not valid!").position(Pos.CENTER).text(ex.getMessage()).showError();
            Email.clear();
            Password.clear();
        }

    }
    @FXML
    public void onRegisterButtonClick() throws IOException {
        Stage thisStage = (Stage) RegisterButton.getScene().getWindow();
        thisStage.hide();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("register.fxml"));
        Stage register_stage = new Stage();
        Scene scene = new Scene(loader.load());
        register_stage.setScene(scene);
        register_stage.show();
        register_stage.setOnCloseRequest(e->thisStage.show());
        register_stage.setOnHidden(e-> thisStage.show());
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("start.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Main menu");
        primaryStage.show();

    }
}
