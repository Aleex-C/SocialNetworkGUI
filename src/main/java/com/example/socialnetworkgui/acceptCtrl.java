package com.example.socialnetworkgui;

import com.example.socialnetworkgui.domain.Prietenie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class acceptCtrl {
    public static ListView<Prietenie> friendRequests;
    @FXML
    public Button acceptFr;
    @FXML
    public ImageView imgTick;
    @FXML
    public void initialize(){
        acceptFr.setGraphic(imgTick);
    }
    @FXML
    public void onAccept(){
        System.out.println(friendRequests.getSelectionModel().getSelectedItem() + "aduagat!!!!!");
        startController.prietenieService.acceptFriend(friendRequests.getSelectionModel().getSelectedItem());
        Stage thisStage = (Stage) acceptFr.getScene().getWindow();
        thisStage.hide();

    }
}
