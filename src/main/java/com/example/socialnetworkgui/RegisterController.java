package com.example.socialnetworkgui;

import com.example.socialnetworkgui.domain.validators.ValidationException;
import com.example.socialnetworkgui.service.UtilizatorService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.security.NoSuchAlgorithmException;

import static javafx.geometry.Pos.CENTER;

public class RegisterController {
    @FXML
    public Button SignUpButton;
    @FXML
    public TextField SUName;
    @FXML
    public TextField SUSurname;
    @FXML
    public TextField SUEmail;

    @FXML
    public PasswordField SUPassword;
    public void OnSignUpButtonClick() throws NoSuchAlgorithmException{
        String lastName = SUName.getText();
        String firstName = SUSurname.getText();
        String email = SUEmail.getText();
        String password = SUPassword.getText();
        Stage thisStage = (Stage) SignUpButton.getScene().getWindow();
        try {
            startController.utilizatorService.addUtilizator(firstName, lastName, email, password);
            SUPassword.clear();
            SUEmail.clear();
            SUSurname.clear();
            SUName.clear();
            Notifications.create().position(CENTER).title("User added successfully!").text("CLICK HERE TO GO BACK").onAction(e->thisStage.hide()).showConfirm();

        } catch (ValidationException ex) {
            Notifications.create().title("Invalid data!").text(ex.getMessage()).showError();
            SUPassword.clear();
            SUEmail.clear();
            SUSurname.clear();
            SUName.clear();
        }

        /**
         * @TODO: - Modify Service for the new User Class
         *        -
         */

    }



}
