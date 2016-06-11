package org.cucumbers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/** Created by Mateusz on 2016-06-10. */

@EnableAutoConfiguration
@Controller
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("")
public class PageController {
    @Autowired
    private ApplicationContext appContext;

    @RequestMapping(value = "/initGame", method = RequestMethod.GET, produces = "text/plain")
    @ResponseBody
    public String startGame(){
        System.out.println("hello");

        return "Hello player, java here";
    }

    @RequestMapping(value = "/placeShip", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String placeShip(@RequestParam("x") int x, @RequestParam("y") int y, @RequestParam("size") int size,
                            @RequestParam("horizontally") boolean horizontally){

        return Boolean.toString(appContext.getBean("player", Player.class).placeShip(x, y, size, horizontally));
    }
}
