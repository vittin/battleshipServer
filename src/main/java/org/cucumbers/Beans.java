package org.cucumbers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** Created by Mateusz on 2016-06-11. */

@Component
@Configuration
public class Beans {
    @Bean
    @Qualifier("player")
    public Player player() {
        return new User(board(), shipsFactory());
    }

    @Bean()
    @Qualifier("cpu")
    public Player cpu() {
        return new Cpu(board(), shipsFactory());
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Board board() {
        return new Board(18);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ShipsFactory shipsFactory() { return new ShipsFactory(3,2,2,2,1);}
}
