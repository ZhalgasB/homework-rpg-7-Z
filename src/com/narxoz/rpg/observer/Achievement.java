package com.narxoz.rpg.observer;

import java.util.HashSet;
import java.util.Set;

public class Achievement implements GameObserver{
    private final Set<String> unlocked = new HashSet<>();
    private int attackCount = 0;

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.ATTACK_LANDED){
            attackCount ++;
            if (attackCount == 1 && unlocked.add("First Hit")){
                System.out.println("ACHIEVE! First HIT");
            }
            if (attackCount >= 10 && unlocked.add("HITTER")){
                System.out.println("ACHIEVE! HITTER 10+ HITS");
            }
        } else if (event.getType() == GameEventType.BOSS_DEFEATED) {
            if (unlocked.add("BOSS SLAYER")){
                System.out.println("ACHIEVE! BOSS SLAYER");
            }
        }
    }
}
