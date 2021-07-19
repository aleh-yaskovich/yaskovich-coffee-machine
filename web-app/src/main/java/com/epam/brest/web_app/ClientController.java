package com.epam.brest.web_app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    /**
     * Show Client page
     * */
    @GetMapping(value = "/client")
    public String showClientPage() {
        return "client";
    }

}
