package org.yanwen.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yanwen.model.Seat;
import org.yanwen.model.Status;
import org.yanwen.model.User;
import org.yanwen.repository.SeatDao;

import java.util.List;

@Service
public class SeatService {
    @Autowired
    private SeatDao seatDao;

    public Seat save(Seat seat){
        return seatDao.save(seat);
    }

    public Seat update(Seat seat){
        return seatDao.update(seat);
    }

    public List<Seat> getAllSeats(Seat seat){
        return seatDao.getSeats();
    }

    public List<Seat> getAllAvailableSeats(){
        return seatDao.getAllAvailableSeats();
    }

    public List<Seat> getSeats(Status status){
        return seatDao.getSeats(status);
    }

    public boolean holdSeats(List<Seat> seats, User user){
        return seatDao.holdSeats(seats,user);
    }

    public boolean reserveSeats(List<Seat> seats, User user){
        return seatDao.reserveSeats(seats, user);
    }

    public Seat getBy(long id){return seatDao.getBy(id);};
}
