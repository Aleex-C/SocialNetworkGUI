package com.example.socialnetworkgui;

import com.example.socialnetworkgui.domain.Message;
import com.example.socialnetworkgui.domain.Prietenie;
import com.example.socialnetworkgui.domain.Utilizator;
import com.example.socialnetworkgui.domain.validators.MessageValidator;
import com.example.socialnetworkgui.domain.validators.PrietenieValidator;
import com.example.socialnetworkgui.domain.validators.UtilizatorValidator;
import com.example.socialnetworkgui.repository.Repository;
import com.example.socialnetworkgui.repository.database.FriendshipsDbRepository;
import com.example.socialnetworkgui.repository.database.MessagesDBRepository;
import com.example.socialnetworkgui.repository.database.UtilizatorDbRepository;
import com.example.socialnetworkgui.service.MesajeService;
import com.example.socialnetworkgui.service.PrietenieService;
import com.example.socialnetworkgui.service.UtilizatorService;
//import com.example.socialnetworkgui.ui.Console;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        String username="postgres";
        String password="postgres";
        String url="jdbc:postgresql://localhost:5432/postgres";
        Repository<Long, Utilizator> repository = new UtilizatorDbRepository(url, username, password, new UtilizatorValidator());
        Repository<Long, Prietenie> repository2 = new FriendshipsDbRepository(url, username, password, new PrietenieValidator());
        Repository<Long, Message> repository3 = new MessagesDBRepository(url, username, password, new MessageValidator());
        UtilizatorService utilizatorService = new UtilizatorService(repository);
        PrietenieService prietenieService = new PrietenieService(repository2);
        MesajeService mesajeService = new MesajeService(repository3);

        startController.utilizatorService = utilizatorService;
        startController.prietenieService = prietenieService;
        startController.mesajeService = mesajeService;
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        startController startController = new startController();
        startController.start(new Stage());
    }
}