package org.yanwen.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yanwen.core.service.SeatService;
import org.yanwen.core.service.UserService;
import org.yanwen.core.service.jms.MessageService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SeatService seatService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;


}
