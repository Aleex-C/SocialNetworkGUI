package com.example.socialnetworkgui.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message extends Entity<Long> {
    private Long id_sender;
    private Long id_receiver;
    private String text;
    private LocalDateTime time;

    public Message(Long id_sender, Long id_receiver, String text, LocalDateTime time) {
        this.id_sender = id_sender;
        this.id_receiver = id_receiver;
        this.text = text;
        this.time=time;
    }

    public Message(Long id_sender, Long id_receiver, String text) {
        this.id_sender = id_sender;
        this.id_receiver = id_receiver;
        this.text = text;
    }

    public Long getId_sender() {
        return id_sender;
    }

    @Override
    public String toString() {
        return "Message:" +
                "id_sender=" + id_sender +
                ", id_receiver=" + id_receiver +
                ", text='" + text + '\'' +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id_sender, message.id_sender) && Objects.equals(id_receiver, message.id_receiver) && Objects.equals(text, message.text) && Objects.equals(time, message.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_sender, id_receiver, text, time);
    }

    public void setId_sender(Long id_sender) {
        this.id_sender = id_sender;
    }

    public Long getId_receiver() {
        return id_receiver;
    }

    public void setId_receiver(Long id_receiver) {
        this.id_receiver = id_receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
