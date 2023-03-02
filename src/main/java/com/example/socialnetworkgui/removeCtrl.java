package com.example.socialnetworkgui;

import com.example.socialnetworkgui.domain.Message;
import com.example.socialnetworkgui.domain.Prietenie;
import com.example.socialnetworkgui.domain.Utilizator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class removeCtrl implements Initializable {
    public static ListView<Prietenie> acceptedRequests;
    @FXML
    public TextArea messageBox;
    @FXML
    public Text friendName;

    public static Utilizator current_user;
    private Long id_receiver;
    @FXML
    public Button deleteFr;
    @FXML
    public Button sendMessage;
    @FXML
    public ListView messagesView;

//    public void setCurrent_user(Utilizator user){
//        this.current_user = current_user;
//    }


    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (acceptedRequests.getSelectionModel().getSelectedItem().getId1().equals(current_user.getId())) {
            id_receiver = acceptedRequests.getSelectionModel().getSelectedItem().getId2();
        } else{
            id_receiver = acceptedRequests.getSelectionModel().getSelectedItem().getId1();
        }
        Optional<Utilizator> user_friend = startController.utilizatorService.getOne(id_receiver);
        friendName.setText(user_friend.get().getFirstName());
        List<Message> msgList = startController.mesajeService.getMessagesBetween(current_user.getId(), id_receiver);
        ObservableList<Message> messages = FXCollections.observableArrayList(msgList);
        messagesView.setItems(messages);
        messagesView.setCellFactory(param -> new ListCell<Message>(){
            @Override
            protected void updateItem(Message item, boolean empty){
                super.updateItem(item, empty);
                if (empty || item == null || item.getId() == null) {
                    setText(null);
                } else {
                    Optional<Utilizator> user_sender = startController.utilizatorService.getOne(item.getId_sender());
                    Optional<Utilizator> user_receiving = startController.utilizatorService.getOne(item.getId_receiver());
                    if (user_sender.get().equals(current_user)) { //daca current_user e "sender", fac fundal albastru
                        setText(item.getText());
                        setStyle("-fx-control-inner-background: derive(deepskyblue, 50%);");
                        setAlignment(Pos.CENTER_RIGHT);
                    }
                    else if (user_receiving.get().equals(current_user)) {
                        setText(item.getText());
                        setStyle("-fx-control-inner-background: derive(palegreen, 50%);");
                    }
                }
            }
        });

    }

    @FXML
    public void onRemoveFriend(){
        System.out.println(acceptedRequests.getSelectionModel().getSelectedItem() + "delete!!!!!");
        startController.prietenieService.deletePrietenie(acceptedRequests.getSelectionModel().getSelectedItem().getId1(), acceptedRequests.getSelectionModel().getSelectedItem().getId2());
        Stage thisStage = (Stage) deleteFr.getScene().getWindow();
        thisStage.hide();

    }
    private void refresh(){
        List<Message> msgList = startController.mesajeService.getMessagesBetween(current_user.getId(), id_receiver);
        ObservableList<Message> messages = FXCollections.observableArrayList(msgList);
        messagesView.setItems(messages);
        messagesView.setCellFactory(param -> new ListCell<Message>(){
            @Override
            protected void updateItem(Message item, boolean empty){
                super.updateItem(item, empty);
                if (empty || item == null || item.getId() == null) {
                    setText(null);
                } else {
                    Optional<Utilizator> user_sender = startController.utilizatorService.getOne(item.getId_sender());
                    Optional<Utilizator> user_receiving = startController.utilizatorService.getOne(item.getId_receiver());
                    if (user_sender.get().equals(current_user)) { //daca current_user e "sender", fac fundal albastru
                        setText(item.getText());
                        setStyle("-fx-control-inner-background: derive(deepskyblue, 50%);");
                        setAlignment(Pos.CENTER_RIGHT);
                    }
                    else if (user_receiving.get().equals(current_user)) {
                        setText(item.getText());
                        setStyle("-fx-control-inner-background: derive(palegreen, 50%);");
                    }
                }
            }
        });
    }

    @FXML
    public void onClickSend(){
        Long id_receiver;
        String text = messageBox.getText();
        messageBox.clear();
        if (acceptedRequests.getSelectionModel().getSelectedItem().getId1().equals(current_user.getId())) {
            id_receiver = acceptedRequests.getSelectionModel().getSelectedItem().getId2();
        } else{
            id_receiver = acceptedRequests.getSelectionModel().getSelectedItem().getId1();
        }
        startController.mesajeService.addMesaj(current_user.getId(), id_receiver, text);
        refresh();
        System.out.println(text);

    }
}
