package org.cucumbers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/** Created by Mateusz on 2016-06-10. */

@EnableAutoConfiguration
@Controller
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("")
public class PageController {
    @RequestMapping(value = "/initGame", method = RequestMethod.GET)
    @ResponseBody
    public String startGame(){
        System.out.println("hello");
        return "Hello player, java here";
    }


}
