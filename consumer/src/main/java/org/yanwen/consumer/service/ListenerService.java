package org.yanwen.consumer.service;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.yanwen.core.domain.Seat;
import org.yanwen.core.domain.Status;
import org.yanwen.core.domain.User;
import org.yanwen.core.service.SeatService;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Service
public class ListenerService{
    @Autowired
    private SeatService seatService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @JmsListener(destination = "MyTicketQueue")
    public void processMessage(String jsonMessage) {
//            String jsonMessage = ((TextMessage)message).getText();
        // get userID and seats information
        JSONObject obj = new JSONObject(jsonMessage);
        logger.info("Received Message: " + jsonMessage);

        // check if the seats are reserved
        // Change status to available if not reserved
        JSONArray jsonSeats = obj.getJSONArray("seats");
        for (int i = 0; i < jsonSeats.length(); i++){
            long seatID = jsonSeats.getJSONObject(i).getLong("id");
            Seat seat = seatService.getBy(seatID);
            User user = seat.getUser();
            if (seatReserved(user))
                setSeatStatus(seat, Status.RESERVED);
            else
                setSeatStatus(seat, Status.AVAILABLE);
        }
    }

    private boolean seatReserved(User user){
        return user.getConfirmation_code() != null &&
                !user.getConfirmation_code().equals("");
    }

    private void setSeatStatus(Seat seat, Status status){
        seat.setStatus(status);
        seatService.update(seat);
    }

}
