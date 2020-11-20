package org.yanwen.controller;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.yanwen.model.Seat;
import org.yanwen.model.Status;
import org.yanwen.model.User;
import org.yanwen.service.MessageService;
import org.yanwen.service.SeatService;
import org.yanwen.service.UserService;
import java.util.*;

@RestController
@RequestMapping(value = "/seats")
public class SeatController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SeatService seatService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    //http://localhost:8080/seats/{RESERVED}
    @RequestMapping(value = "/{status}", method = RequestMethod.GET)
    public String getSeatsByStatus(@PathVariable(name = "status") Status status) {
        if (status.equals(Status.AVAILABLE) || status.equals(Status.HOLD) || status.equals(Status.RESERVED)) {
            List<Seat> seats = seatService.getSeats(status);
            String result = "There are " + seats + " under the given status.";
            return result;
        }
        return null;
    }

    //http://localhost:8080/seats/reserve/{1}
    @RequestMapping(value = "/reserve/{userId}", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void reserveSeat(@RequestBody Set<Seat> seats, @PathVariable(name = "userId") Long id){
        List<Seat> seatsFromDB = new ArrayList<>();
        for (Seat s: seats){
            s = seatService.getBy(s.getId());
            seatsFromDB.add(s);
        }
        User user = userService.getByID(id);
        if (seatService.reserveSeats(seatsFromDB, user)){
            logger.info("success");
        }else
            logger.error("error");
        logger.debug(seatsFromDB.toString());
    }

    //http://localhost:8080/seats/hold/{1}
    @RequestMapping(value = "/hold/{userId}", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void holdSeat(@RequestBody Set<Seat> seats, @PathVariable(name = "userId") Long id){
        List<Seat> seatsFromDB = new ArrayList<>();
        for (Seat s: seats){
            s = seatService.getBy(s.getId());
            seatsFromDB.add(s);
        }
        User user = userService.getByID(id);
        if (seatService.holdSeats(seatsFromDB, user)){
            logger.info("success");

            //Put all info to JSON
            JSONObject obj = new JSONObject();
            JSONArray seatArray = new JSONArray();
            obj.put("UserId", id);

            for (int i = 0; i < seatsFromDB.size(); i++){
                JSONObject object = new JSONObject();
                object.put("id", seatsFromDB.get(i).getId());
                seatArray.put(object);
            }
            obj.put("seats",seatArray);

            //30 sec
            messageService.sendMessage(obj.toString(),10);
        }else
            logger.error("error");
        logger.debug(seatsFromDB.toString());
    }
}