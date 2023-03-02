package com.example.socialnetworkgui.repository.database;

import com.example.socialnetworkgui.domain.Message;
import com.example.socialnetworkgui.domain.Prietenie;
import com.example.socialnetworkgui.domain.validators.Validator;
import com.example.socialnetworkgui.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MessagesDBRepository implements Repository<Long, Message> {
    private String url;
    private String username;
    private String password;

    private Validator<Message> validator;

    public MessagesDBRepository(String url, String username, String password, Validator<Message> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }
    @Override
    public Optional<Message> findOne(Long aLong){ return Optional.empty();}
    @Override
    public Iterable<Message> findAll() {
        Set<Message> messages = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from mesaje");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long id_sender = resultSet.getLong("id_sender");
                Long id_receiver = resultSet.getLong("id_receiver");
                LocalDateTime date = new java.sql.Timestamp(resultSet.getTimestamp("sentTime").getTime()).toLocalDateTime();
                String mesaj = resultSet.getString("mesaj");
                Message textMessage = new Message(id_sender, id_receiver, mesaj, date);
                textMessage.setId(id);
                messages.add(textMessage);
            }
            return messages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
    @Override
    public Optional<Message> save(Message entity){
        validator.validate(entity);
        String sql = "insert into mesaje (id_sender, id_receiver, mesaj) values (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, entity.getId_sender());
            ps.setLong(2, entity.getId_receiver());
            ps.setString(3, entity.getText());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public Optional<Message> delete (Long aLong){
        String sql = "delete from mesaje where id = ?";
        int affRows = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, aLong);
            affRows = ps.executeUpdate();
            System.out.println("Number of affected rows: ");
            System.out.println(affRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();}
    @Override
    public Optional<Message> update(Message entity) {return Optional.empty();}
    @Override
    public Optional<Message> accept (Message entity){return Optional.empty();}

}
