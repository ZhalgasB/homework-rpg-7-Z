package com.narxoz.rpg.observer;

import com.narxoz.rpg.combatant.Hero;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Support implements GameObserver{
    private final List<Hero> heroes;
    private final Random random = new Random();

    public Support(List<Hero> heroes) {
        this.heroes = heroes;
    }



    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.HERO_LOW_HP){
            List<Hero> alive = new ArrayList<>();

            for (Hero hero : heroes){
                if (hero.isAlive()){
                    alive.add(hero);
                }
            }

            if (!alive.isEmpty()){
                Hero target = alive.get(random.nextInt(alive.size()));
                target.heal(25);
                System.out.println("Healed: " + target.getName());
            }
        }
    }
}
