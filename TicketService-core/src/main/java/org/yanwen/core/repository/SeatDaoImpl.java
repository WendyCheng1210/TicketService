package org.yanwen.core.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yanwen.core.domain.Seat;
import org.yanwen.core.domain.Status;
import org.yanwen.core.domain.User;

import java.util.List;
import java.util.Random;

@Repository
public class SeatDaoImpl implements SeatDao {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserDao userDao;

    @Override
    public Seat getBy(long id) {
        String hql = "From Seat as s where s.id = :Id";
        Session session = sessionFactory.openSession();
        try{
            Query<Seat> query = session.createQuery(hql);
            query.setParameter("Id",id);
            Seat result = query.uniqueResult();
            session.close();
            return result;
        }catch (HibernateException e){
            logger.error("failur to get data record");
            session.close();
            return null;
        }
    }

    @Override
    public Seat save(Seat seat) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(seat);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage(), e);
        }
        if (seat != null) logger.debug(String.format("The user was insert failed"));
        return seat;
    }

    @Override
    public List<Seat> getSeats() {
        String hql = "From Seat";
        try(Session session = sessionFactory.openSession()){
            Query<Seat> query = session.createQuery(hql);
            return query.list();
        }
    }

    @Override
    public Seat update(Seat seat) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(seat);
            transaction.commit();
            return seat;
        }catch (Exception e){
            if (transaction != null) transaction.rollback();
            logger.error("failure to update record", e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(Seat seat) {
        String hql = "Delete Seat as s where s.id = :Id";
        int deletedCount = 0;
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            Query<Seat> query = session.createQuery(hql);
            query.setParameter("Id", seat.getId());
            deletedCount = query.executeUpdate();
            transaction.commit();
            session.close();
            return deletedCount >= 1 ? true : false;
        }catch (HibernateException e ){
            if (transaction != null) transaction.rollback();
            session.close();
            logger.error("unable to delete record",e);
        }
        return false;
    }

    @Override
    public List<Seat> getAllAvailableSeats() {
        return getSeats(Status.AVAILABLE);
    }

    @Override
    public List<Seat> getSeats(Status status) {
        String hql = "From Seat as s where s.status = :status";
        try(Session session = sessionFactory.openSession()){
            Query<Seat> query = session.createQuery(hql);
            query.setParameter("status", status);
            return query.list();
        }
    }

    @Override
    public boolean holdSeats(List<Seat> seats, User user) {
        for (Seat seat: seats){
            seat.setStatus(Status.HOLD);
            seat.setUser(user);
            Seat result = update(seat);
            if (result == null)
                return false;
        }
        return true;
    }

    @Override
    public boolean reserveSeats(List<Seat> seats, User user) {
        boolean allHold = true;
        for (Seat seat: seats){
//            seat.setStatus(Status.RESERVED);
            if (seat.getStatus() != Status.HOLD){
//                create a confirmation code
                allHold = false;
            }
//            seat.setUser(user);
//            Seat result = update(seat);
//            if (result == null)
//                return false;
        }
        if (allHold){
            //confirmation code
            Random rand = new Random();
            int randomCode = rand.nextInt(100000);

            //assign cc to user
            user.setConfirmation_code(Integer.toString(randomCode));

            //update user to database
            userDao.update(user);
        }else {
            return false;
        }
        return true;
    }
}
