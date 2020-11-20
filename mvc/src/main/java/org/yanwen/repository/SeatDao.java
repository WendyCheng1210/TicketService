package org.yanwen.repository;
import org.yanwen.model.Seat;
import org.yanwen.model.Status;
import org.yanwen.model.User;
import java.util.List;

public interface SeatDao {
    Seat getBy(long id);
    Seat save(Seat seat);
    List<Seat> getSeats();
    Seat update(Seat seat);
    boolean delete(Seat seat);
    List<Seat> getAllAvailableSeats();
    List<Seat> getSeats(Status status);
    boolean holdSeats(List<Seat> seats,User user);
    boolean reserveSeats(List<Seat> seats, User user);
}
