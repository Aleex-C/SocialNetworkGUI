package com.example.socialnetworkgui.service;

import com.example.socialnetworkgui.domain.Message;
import com.example.socialnetworkgui.domain.Utilizator;
import com.example.socialnetworkgui.repository.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class MesajeService {
    private Repository<Long, Message> repoMesaje;

    public MesajeService(Repository<Long, Message> repoMesaje) {
        this.repoMesaje = repoMesaje;
    }

    public void addMesaj(Long id_sender, Long id_receiver, String mesaj){
        Message m = new Message(id_sender, id_receiver, mesaj);
        repoMesaje.save(m);
    }
    public void deleteMesaj(Long id_mesaj){
        repoMesaje.delete(id_mesaj);
    }
    public Iterable<Message> getAll(){return repoMesaje.findAll();}
    public List<Message> getMessagesTo(Utilizator user){
        Iterable<Message> all = repoMesaje.findAll();
        Optional<List<Message>> result = Optional.of(StreamSupport.stream(all.spliterator(), false)
                .filter(p -> p.getId_receiver().equals(user.getId()))
                .sorted(Comparator.comparing(Message::getTime))
                .toList());
        if (result.isEmpty())
            throw new RuntimeException("No messages for this user!");
        return result.get();
    }
    public List<Message> getMessagesBetween(Long id_user_sender, Long id_user_receiver){
        Iterable<Message> all = repoMesaje.findAll();
        Optional<List<Message>> result = Optional.of(StreamSupport.stream(all.spliterator(), false)
                .filter(p -> (p.getId_receiver().equals(id_user_receiver) && p.getId_sender().equals(id_user_sender))
                        || (p.getId_receiver().equals(id_user_sender) && p.getId_sender().equals(id_user_receiver)))
                .sorted(Comparator.comparing(Message::getTime))
                .toList());
        if (result.isEmpty())
            throw new RuntimeException("No messages for this user!");
        return result.get();
    }
}
