package org.cucumbers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.*;

/** Created by Mateusz on 2016-06-10. */

@EnableAutoConfiguration
@RestController
@CrossOrigin(origins = "http://localhost:63343")
@RequestMapping("")
public class PageController {

    private ApplicationContext context;
    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    @RequestMapping(value = "/initGame", method = RequestMethod.GET, produces = "text/plain")

    public String startGame(){
        context = new AnnotationConfigApplicationContext(Beans.class);
        setPlayer(context.getBean("player", Player.class));
        return "Hello player, java here";
    }

    @RequestMapping(value = "/checkShip", method = RequestMethod.GET, produces = "text/plain")
    public String checkShip(@RequestParam("size") int size){
        return Boolean.toString(player.remainingShips(size) > 0);
    }


    @RequestMapping(value = "/placeShip", method = RequestMethod.POST, produces = "application/json")
    public String placeShip(@RequestParam("x") int x, @RequestParam("y") int y, @RequestParam("size") int size,
                               @RequestParam("horizontally") boolean horizontally){
        boolean response = player.placeShip(x,y,size,horizontally);
        String JSONString = String.format("{\n" +
                "  \"response\": \"%s\",\n" +
                "  \"remaining\": \"%d\",\n" +
                "  \"complete\": \"%s\"\n" +
                "}", response, player.remainingShips(size), player.canStartGame());
        System.out.println(JSONString);

        return JSONString;
    }
}
