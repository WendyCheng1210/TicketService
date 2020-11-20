package org.yanwen.consumer.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ListenerService implements MessageListener {

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


        }catch (JMSException e){
            logger.error("Unable to receive the message");
            e.printStackTrace();
        }
    }
}
