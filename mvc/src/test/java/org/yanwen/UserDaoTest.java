package org.yanwen;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yanwen.model.Seat;
import org.yanwen.model.Status;
import org.yanwen.model.User;
import org.yanwen.repository.SeatDao;
import org.yanwen.repository.UserDao;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Autowired
    private SeatDao seatDao;

    private User u1;
    private Seat s1;
    private Seat s2;

    @Before
    public void setUp(){
        u1 = new User();
        u1.setName("Ann");
        u1.setConfirmation_code("ann12345");
        u1.setEmail("ann@hotmail.com");
        u1.setPhone("(850)-998-4222");
        u1 = userDao.save(u1);

        Instant startTime = Instant.now();
        s1 = new Seat();
        s1.setRow_number(1);
        s1.setSeat_number(2);
        s1.setStatus(Status.RESERVED);
        s1.setUser(u1);
        seatDao.save(s1);

        s2 = new Seat();
        s2.setRow_number(1);
        s2.setSeat_number(3);
        s2.setStatus(Status.HOLD);
        s2.setUser(u1);
        seatDao.save(s2);
    }


    @After
    public void tearDown(){
        seatDao.delete(s1);
        seatDao.delete(s2);
        userDao.delete(u1);
    }

    @Test
    public void getUserTest(){
        List<User> users = userDao.getAllUsers();
        int expectedNumOfUser = 1;
        Assert.assertEquals(expectedNumOfUser, users.size());
    }

    public void getUserEagerByTest(){
        User user = userDao.getEagerBy(u1.getId());
        assertNotNull(user);
        assertEquals(user.getName(),u1.getName());
        assertTrue(user.getSeats().size()>0);
    }
}


