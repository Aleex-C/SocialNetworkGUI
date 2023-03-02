package com.example.socialnetworkgui.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Prietenie extends Entity<Long> {
    Long id1;
    Long id2;
    LocalDateTime friendsFrom;

    String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prietenie prietenie = (Prietenie) o;
        return Objects.equals(id1, prietenie.id1) && Objects.equals(id2, prietenie.id2) && Objects.equals(friendsFrom, prietenie.friendsFrom) && Objects.equals(status, prietenie.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id1, id2, friendsFrom, status);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId1() {
        return id1;
    }

    public Prietenie(Long id1, Long id2, LocalDateTime friendsFrom, String status) {
        this.id1 = id1;
        this.id2 = id2;
        this.friendsFrom = friendsFrom;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Prietenie{" +
                "id1=" + id1 +
                ", id2=" + id2 +
                ", friendsFrom=" + friendsFrom +
                '}';
    }

    public Prietenie(Long id1, Long id2, LocalDateTime friendsFrom) {
        this.id1 = id1;
        this.id2 = id2;
        this.friendsFrom = friendsFrom;
    }

    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
    }

    public void setFriendsFrom(LocalDateTime friendsFrom) {
        this.friendsFrom = friendsFrom;
    }

    public void setId1(Long id1) {
        this.id1 = id1;
    }

    public Long getId2() {
        return id2;
    }

    public void setId2(Long id2) {
        this.id2 = id2;
    }

    public Prietenie(Long id1, Long id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

}
