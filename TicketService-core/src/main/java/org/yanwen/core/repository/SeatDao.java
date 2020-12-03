package org.yanwen.core.repository;
import org.yanwen.core.domain.Seat;
import org.yanwen.core.domain.Status;
import org.yanwen.core.domain.User;

import java.util.List;

public interface SeatDao {
    Seat getBy(long id);
    Seat save(Seat seat);
    List<Seat> getSeats();
    Seat update(Seat seat);
    boolean delete(Seat seat);
    List<Seat> getAllAvailableSeats();
    List<Seat> getSeats(Status status);
    boolean holdSeats(List<Seat> seats, User user);
    boolean reserveSeats(List<Seat> seats, User user);
}
