package org.yanwen.core.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "seats")
public class Seat {
    public Seat(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "row_number")
    private int row_number;

    @Column(name = "seat_number")
    private int seat_number;

    @Enumerated
    @Column(name = "status", columnDefinition = "int")
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRow_number() {
        return row_number;
    }

    public void setRow_number(int row_number) {
        this.row_number = row_number;
    }

    public int getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(int seat_number) {
        this.seat_number = seat_number;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return id == seat.id &&
                row_number == seat.row_number &&
                seat_number == seat.seat_number &&
                status == seat.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, row_number, seat_number, status);
    }

    @Override
    public String toString() {
        return row_number + "," + seat_number + ": " + status;
    }
}
