package org.yanwen.consumer.service;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.yanwen.core.domain.Seat;
import org.yanwen.core.domain.Status;
import org.yanwen.core.domain.User;
import org.yanwen.core.service.jms.SeatService;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
@EnableAutoConfiguration
public class ListenerService implements MessageListener {
    @Autowired
    private SeatService seatService;

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void onMessage(Message message) {
        try{
            String jsonMessage = ((TextMessage)message).getText();

            // get userID and seats information
            JSONObject obj = new JSONObject(jsonMessage);
            logger.info("Received Message: " + ((TextMessage)message).getText());

            // check if the seats are reserved
            // Change status to available if not reserved
            JSONArray jsonSeats = obj.getJSONArray("seats");
            for (int i = 0; i < jsonSeats.length(); i++){
                long seatID = jsonSeats.getJSONObject(i).getLong("id");
                Seat seat = new Seat();
                seat.setId(seatID);
                if (seatReserved(seat))
                    setSeatStatus(seat, Status.RESERVED);
                else
                    setSeatStatus(seat, Status.AVAILABLE);
            }

        }catch (JMSException e){
            logger.error("Unable to receive the message");
            e.printStackTrace();
        }
    }

    private boolean seatReserved(Seat seat){
        seat = seatService.getBy(seat.getId());
        User user = seat.getUser();
        return !user.getConfirmation_code().equals("");
    }

    private void setSeatStatus(Seat seat, Status status){
        seat.setStatus(status);
        seatService.update(seat);
    }

}
