package com.example.socialnetworkgui.repository.database;

import com.example.socialnetworkgui.domain.Prietenie;
import com.example.socialnetworkgui.domain.Utilizator;
import com.example.socialnetworkgui.domain.validators.Validator;
import com.example.socialnetworkgui.repository.Repository;
import com.example.socialnetworkgui.service.PrietenieService;

import java.sql.*;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class FriendshipsDbRepository implements Repository<Long, Prietenie> {
    private String url;
    private String username;
    private String password;
    private Validator<Prietenie> validator;

    public FriendshipsDbRepository(String url, String username, String password, Validator<Prietenie> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }
    @Override
    public Optional<Prietenie> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Prietenie> findAll() {
        Set<Prietenie> friendships = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from prietenii");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id_p");
                Long id_1 = resultSet.getLong("id_1");
                Long id_2 = resultSet.getLong("id_2");
                LocalDateTime date = new java.sql.Timestamp(resultSet.getTimestamp("date").getTime()).toLocalDateTime();
                String status = resultSet.getString("status");
                Prietenie friend = new Prietenie(id_1,id_2, date, status);
                friend.setId(id);
                friendships.add(friend);
            }
            return friendships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
    }


    @Override
    public Optional<Prietenie> save(Prietenie entity) {
        validator.validate(entity);
        String sql = "insert into prietenii (id_1, id_2) values (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, entity.getId1());
            ps.setLong(2, entity.getId2());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Prietenie> delete(Long aLong) {
        String sql = "delete from prietenii where id_p = ?";
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
        return Optional.empty();
    }

    @Override
    public Optional<Prietenie> update(Prietenie entity) {
        validator.validate(entity);
        String sql = "update prietenii set id_1 = ?, id_2 = ? WHERE id_p = ?";
        //int affRows = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, entity.getId1());
            ps.setLong(2, entity.getId2());
            ps.setLong(3, entity.getId());
            ps.executeUpdate();
            //System.out.println("Number of affected rows: ");
            //System.out.println(affRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    public Optional<Prietenie> accept(Prietenie entity){
        String sql = "update prietenii set status = 'accepted', date = (now())::timestamp without time zone WHERE id_p = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
