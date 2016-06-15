package org.cucumbers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.*;

/** Created by Mateusz on 2016-06-10. */

@EnableAutoConfiguration
@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/api")
public class PageController {

    private ApplicationContext context;
    private Player player;
    private Player opponent;

    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    @RequestMapping(value = "/initGame", method = RequestMethod.GET, produces = "text/plain")
    public String startGame(){
        context = new AnnotationConfigApplicationContext(Beans.class);
        setPlayer(context.getBean("player", Player.class));
        setOpponent(context.getBean("cpu", Player.class));
        if (!opponent.isHuman()){
            Cpu opponent = (Cpu) this.opponent;
            opponent.fillBoard();
        }
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
        return JSONString;
    }

    @RequestMapping(value = "/shoot", method = RequestMethod.POST, produces = "application/json")
    public String shoot(@RequestParam("x") int x, @RequestParam("y") int y){

        boolean shipDestroyed = player.isShipFinished();
        boolean success = true;
        boolean hit;

        try {
            hit = player.shootTo(x, y);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            hit = false;
            success = false;
        }

        String response = String.format("{\n" +
                "  \"hit\": \"%s\",\n" +
                "  \"dead\": \"%s\",\n" +
                "  \"success\": \"%s\"\n" +
                "}", hit, shipDestroyed, success);

        //todo: System.out.println(response);
        return response;


    }

    @RequestMapping(value = "/getShoot", method = RequestMethod.GET, produces = "application/json")
    public String getShoot(){

        if (!opponent.isHuman()){
            ComputerPlayer cpu = (ComputerPlayer) opponent;
            cpu.generateShoot();
        }

        int coordinates[] = opponent.getShootCoordinates();
        boolean hit = opponent.isHit();

        String response = String.format("{\n" +
                "  \"x\": \"%d\",\n" +
                "  \"y\": \"%d\",\n" +
                "  \"hit\": \"%s\"\n" +
                "}", coordinates[0], coordinates[1], hit);

        System.out.println(response);
        return response;
    }

    @RequestMapping(value = "/endGame", method = RequestMethod.GET, produces = "application/json")
    public String isEndGame(){

        String response = String.format("{\n" +
                "  \"isEndGame\": \"%s\"\n" +
                "}", player.isEndGame() || opponent.isEndGame());

        return response;
    }


}
