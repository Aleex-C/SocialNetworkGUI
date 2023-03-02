package com.example.socialnetworkgui;

import com.example.socialnetworkgui.domain.Message;
import com.example.socialnetworkgui.domain.Pair;
import com.example.socialnetworkgui.domain.Prietenie;
import com.example.socialnetworkgui.domain.Utilizator;
import com.example.socialnetworkgui.domain.validators.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.geometry.Pos.CENTER;

public class MenuController implements Initializable {
    public static Utilizator current_user;
    @FXML
    public TextField greetingMessage;
    @FXML
    public TitledPane Notifications;
    @FXML
    public TitledPane Sent;
    @FXML
    public TitledPane myFriends;
    @FXML
    public ListView<Prietenie> friendRequests;
    @FXML
    public ListView<Prietenie> acceptedRequests;
    @FXML
    public ListView<Prietenie> sentRequests;
    @FXML
    public ListView<Message> messajeView;
    @FXML
    public Button addFriend;
    @FXML
    public TextField friendEmail;
    @FXML
    public Button deleteUser;
    @FXML
    public void onDeleteUser(){
        Stage thisStage = (Stage) deleteUser.getScene().getWindow();
        startController.utilizatorService.deleteUtilizator(current_user.getId());
        List<Pair> deSters = startController.prietenieService.findAllUsersWhoWereFriends(current_user.getId());
        for (Pair p :
                deSters) {
            startController.prietenieService.deletePrietenie(p.getId1(), p.getId2());
        }
        org.controlsfx.control.Notifications.create().position(CENTER).title("User removed!").text("CLICK HERE TO GO BACK TO THE LOGIN PAGE!").onAction(e->thisStage.hide()).showConfirm();
        //thisStage.hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        greetingMessage.setPromptText("Greetings, " + current_user.getFirstName() + "!\rWhat are you thinking about today?");
        List<Prietenie> frReq = startController.prietenieService.getPendingFriends(current_user);
        ObservableList<Prietenie> requests = FXCollections.observableArrayList(frReq);
        /**
         * Possible fix for the weird graphical thing on notification would be streaming the "getPendingFriend"
         * then filter only those that have the second ID identical to the current_user!
         */
        friendRequests.setItems(requests);
        friendRequests.setCellFactory(param -> new ListCell<Prietenie>(){
            @Override
            protected void updateItem(Prietenie item, boolean empty){
                super.updateItem(item, empty);
                if (empty || item == null || item.getId() == null) {
                    setText(null);
                } else {
                    Optional<Utilizator> user1 = startController.utilizatorService.getOne(item.getId1());
                    Optional<Utilizator> user2 = startController.utilizatorService.getOne(item.getId2());
                    if (user2.get().equals(current_user)) { //daca current_user e "receiver", setez cine trimite
                        setText(user1.get().getLastName() + " " + user1.get().getFirstName());
                    }
                    else{
                        setText(null);
                    }
                }
            }
        });
        List<Prietenie> accFriends = startController.prietenieService.getAcceptedFriends(current_user);
        ObservableList<Prietenie> accepted = FXCollections.observableArrayList(accFriends);
        acceptedRequests.setItems(accepted);
        acceptedRequests.setCellFactory(param -> new ListCell<Prietenie>(){
            @Override
            protected void updateItem(Prietenie item, boolean empty){
                super.updateItem(item, empty);
                if (empty || item == null || item.getId() == null) {
                    setText(null);
                } else {
                    Optional<Utilizator> user1 = startController.utilizatorService.getOne(item.getId1());
                    Optional<Utilizator> user2 = startController.utilizatorService.getOne(item.getId2());
                    if (!user1.get().equals(current_user)){
                        setText(user1.get().getLastName() + " " + user1.get().getFirstName() + " " + item.getFriendsFrom());
                    }
                    else{
                        setText(user2.get().getLastName() + " " + user2.get().getFirstName() + " " + item.getFriendsFrom());
                    }
                }
            }
        });

        //------------------------

        List<Prietenie> sntReq = startController.prietenieService.getPendingFriends(current_user);
        ObservableList<Prietenie> sntRequests = FXCollections.observableArrayList(sntReq);
        /**
         * Possible fix for the weird graphical thing on notification would be streaming the "getPendingFriend"
         * then filter only those that have the second ID identical to the current_user!
         */
        sentRequests.setItems(sntRequests);
        sentRequests.setCellFactory(param -> new ListCell<Prietenie>(){
            @Override
            protected void updateItem(Prietenie item, boolean empty){
                super.updateItem(item, empty);
                if (empty || item == null || item.getId() == null) {
                    setText(null);
                } else {
                    Optional<Utilizator> user1 = startController.utilizatorService.getOne(item.getId1());
                    Optional<Utilizator> user2 = startController.utilizatorService.getOne(item.getId2());
                    if (!user2.get().equals(current_user)) { //daca current_user e "receiver", setez cine trimite
                        setText(user2.get().getLastName() + " " + user2.get().getFirstName());
                    }
                    else{
                        setText(null);
                    }
                }
            }
        });


    }
    public void refresh(){
        List<Prietenie> frReq = startController.prietenieService.getPendingFriends(current_user);
        ObservableList<Prietenie> requests = FXCollections.observableArrayList(frReq);
        friendRequests.setItems(requests);
        friendRequests.setCellFactory(param -> new ListCell<Prietenie>(){
            @Override
            protected void updateItem(Prietenie item, boolean empty){
                super.updateItem(item, empty);
                if (empty || item == null || item.getId() == null) {
                    setText(null);
                } else {
                    Optional<Utilizator> user1 = startController.utilizatorService.getOne(item.getId1());
                    Optional<Utilizator> user2 = startController.utilizatorService.getOne(item.getId2());
                    if (user2.get().equals(current_user)){ //daca current_user e "receiver", setez cine trimite
                        setText(user1.get().getLastName() + " " + user1.get().getFirstName());
                    }
                }
            }
        });
        List<Prietenie> accFriends = startController.prietenieService.getAcceptedFriends(current_user);
        ObservableList<Prietenie> accepted = FXCollections.observableArrayList(accFriends);
        acceptedRequests.setItems(accepted);
        acceptedRequests.setCellFactory(param -> new ListCell<Prietenie>(){
            @Override
            protected void updateItem(Prietenie item, boolean empty){
                super.updateItem(item, empty);
                if (empty || item == null || item.getId() == null) {
                    setText(null);
                } else {
                    Optional<Utilizator> user1 = startController.utilizatorService.getOne(item.getId1());
                    Optional<Utilizator> user2 = startController.utilizatorService.getOne(item.getId2());
                    if (!user1.get().equals(current_user)){
                        setText(user1.get().getLastName() + " " + user1.get().getFirstName() + " " + item.getFriendsFrom());
                    }
                    else{
                        setText(user2.get().getLastName() + " " + user2.get().getFirstName() + " " + item.getFriendsFrom());
                    }
                }
            }
        });

        List<Prietenie> sntReq = startController.prietenieService.getPendingFriends(current_user);
        ObservableList<Prietenie> sntRequests = FXCollections.observableArrayList(sntReq);
        /**
         * Possible fix for the weird graphical thing on notification would be streaming the "getPendingFriend"
         * then filter only those that have the second ID identical to the current_user!
         */
        sentRequests.setItems(sntRequests);
        sentRequests.setCellFactory(param -> new ListCell<Prietenie>(){
            @Override
            protected void updateItem(Prietenie item, boolean empty){
                super.updateItem(item, empty);
                if (empty || item == null || item.getId() == null) {
                    setText(null);
                } else {
                    Optional<Utilizator> user1 = startController.utilizatorService.getOne(item.getId1());
                    Optional<Utilizator> user2 = startController.utilizatorService.getOne(item.getId2());
                    if (!user2.get().equals(current_user)) { //daca current_user e "receiver", setez cine trimite
                        setText(user2.get().getLastName() + " " + user2.get().getFirstName());
                    }
                    else{
                        setText(null);
                    }
                }
            }
        });
    }

    @FXML
    public void itemSelected() throws IOException {
        System.out.println("clicked on " + friendRequests.getSelectionModel().getSelectedItem());
        if (friendRequests.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("accept.fxml"));
            acceptCtrl.friendRequests = friendRequests;
            Stage acceptStage = new Stage();
            Scene scene = new Scene(loader.load());
            acceptStage.setScene(scene);
            acceptStage.show();
            acceptStage.setOnHidden(e -> refresh());
        }
    }
    @FXML
    public void itemSelectedAccepted() throws IOException {
        System.out.println("clicked on " + acceptedRequests.getSelectionModel().getSelectedItem());
        if (acceptedRequests.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("remove_friend.fxml"));
            removeCtrl.acceptedRequests = acceptedRequests;
            removeCtrl.current_user = current_user;
            Stage removeStage = new Stage();
            Scene scene = new Scene(loader.load());
            removeStage.setScene(scene);
            removeStage.show();
            removeStage.setOnHidden(e-> refresh());
        }
    }
    @FXML
    public void itemSelectedSent() throws IOException {
        System.out.println("clicked on " + sentRequests.getSelectionModel().getSelectedItem());
        if (sentRequests.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("remove_friend.fxml"));
            removeCtrl.acceptedRequests = sentRequests;
            Stage removeStage = new Stage();
            Scene scene = new Scene(loader.load());
            removeStage.setScene(scene);
            removeStage.show();
            removeStage.setOnHidden(e-> refresh());
        }
    }

    @FXML
    public void onAddFriendClick() throws IOException {
        String frEmail = friendEmail.getText();
        Stage thisStage = (Stage) addFriend.getScene().getWindow();
        friendEmail.clear();
        Optional<Utilizator> newFriend = startController.utilizatorService.findByEmail(frEmail);
        if(newFriend.isPresent()){
            try{
                startController.prietenieService.addPrietenie(current_user.getId(), newFriend.get().getId());
                org.controlsfx.control.Notifications.create().text("Request sent!").showConfirm();
                refresh();
            }catch (ValidationException ex){
                org.controlsfx.control.Notifications.create().text("Error!").text(ex.getMessage()).showWarning();
            }
        }
        else {
            org.controlsfx.control.Notifications.create().text("No user with this email!").showWarning();
        }
    }

    private class CustomCell extends ListCell<Prietenie> {
        private Text name;
        public CustomCell(){
            super();
            name = new Text();
        }
        @Override
        protected void updateItem(Prietenie item, boolean empty){
            super.updateItem(item, empty);
            if (empty || item == null || item.getId() == null) {
                setText(null);
            } else {
                Optional<Utilizator> user1 = startController.utilizatorService.getOne(item.getId1());
                Optional<Utilizator> user2 = startController.utilizatorService.getOne(item.getId2());
                if (!user1.get().equals(current_user)){
                    name.setText(user1.get().getLastName() + " " + user1.get().getFirstName() + " " + user1.get().getEmail());
                }
                else{
                    name.setText(user2.get().getLastName() + " " + user2.get().getFirstName() + " " + user2.get().getEmail());
                }
            }
        }
    }

}
