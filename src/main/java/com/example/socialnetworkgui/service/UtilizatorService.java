package com.example.socialnetworkgui.service;

import com.example.socialnetworkgui.domain.Utilizator;
import com.example.socialnetworkgui.domain.validators.ValidationException;
import com.example.socialnetworkgui.repository.Repository;
import com.example.socialnetworkgui.repository.Repository0;
import com.example.socialnetworkgui.repository.file.UtilizatorFile0;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class UtilizatorService {
    private final Repository<Long, Utilizator> repo;

    public UtilizatorService(Repository<Long, Utilizator> repo) {
        this.repo = repo;
    }

    public void addUtilizator(String firstName, String lastsName, String email, String password){
        Utilizator e = new Utilizator(firstName, lastsName, email, password);
        Optional<Utilizator> user = repo.save(e);
    }
    public Optional <Utilizator> findByEmail(String email){
        Iterable<Utilizator> users = repo.findAll();
        Optional<Utilizator> result = StreamSupport.stream(users.spliterator(), false)
                .filter(p -> p.getEmail().equals(email))
                .findFirst();
        return result;
    }
    public Utilizator checkPassword(String email, String password){
        List<Utilizator> users = new ArrayList<>((Collection) repo.findAll());
        Optional<Utilizator> result = users.stream()
                .filter(p -> p.getPassword().equals(password))
                .filter(p -> p.getEmail().equals(email))
                .findFirst();
        if (result.isEmpty())
            throw new ValidationException("Wrong password or/and email!");
        return result.get();
    }
    public void deleteUtilizator(Long id){
        repo.delete(id);
    }
    public Iterable<Utilizator> getAll(){
        return repo.findAll();
    }
    public Optional<Utilizator> getOne(Long id){
        return repo.findOne(id);
    }
    public void update(Long id, String prenume_nou, String nume_nou){
        Utilizator e = new Utilizator(prenume_nou, nume_nou);
        e.setId(id);
        repo.update(e);
    }
}
