package com.narxoz.rpg.observer;

import com.narxoz.rpg.combatant.Hero;

import java.util.List;

public class HeroStatus implements  GameObserver{
    private final List<Hero> heroes;

    public HeroStatus(List<Hero> heroes) {
        this.heroes = heroes;
    }



    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.HERO_LOW_HP || event.getType() == GameEventType.HERO_DIED){
            System.out.println("Hero Status: ");

            for (Hero h : heroes){
                String status;

                if (h.isAlive()){
                    status = "HP: " + h.getHp() + h.getMaxHp();
                }
                else {
                    status = "DEAD";
                }
                System.out.println(h.getName() + ": " + status);

            }
        }
    }
}
