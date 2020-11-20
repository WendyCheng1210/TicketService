package org.yanwen;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yanwen.model.Seat;
import org.yanwen.repository.SeatDao;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class SeatDaoTest {
    @Autowired
    private SeatDao seatDao;

    @Test
    public void testGetAllAvaliableSeats(){
        List<Seat> seats = seatDao.getAllAvailableSeats();
        Assert.assertEquals(5, seats.size());
    }

}
