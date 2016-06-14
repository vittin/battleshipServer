package org.cucumbers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Created by Mateusz on 2016-06-11. */

@Configuration
public class Beans {
    @Bean
    public Player player() {
        return new User(board(), shipsFactory());
    }

    @Bean
    public Cpu cpu() {
        return new Cpu(board(), shipsFactory());
    }

    @Bean
    public Board board() {
        return new Board(18);
    }

    @Bean
    public ShipsFactory shipsFactory() { return new ShipsFactory(3,2,2,2,1);}
}
