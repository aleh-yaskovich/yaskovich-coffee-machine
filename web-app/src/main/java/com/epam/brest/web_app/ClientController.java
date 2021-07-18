package com.epam.brest.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

    /**
     * Show Client page
     * */
    @GetMapping(value = "/client")
    public String showClientPage() {
        return "client";
    }

}
