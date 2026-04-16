package com.narxoz.rpg.observer;

public class BattleLogger implements GameObserver{
    @Override
    public void onEvent(GameEvent event) {
        String ms = switch (event.getType()) {
            case ATTACK_LANDED -> event.getSourceName() + " Lands" + event.getValue() + "damage";
            case HERO_LOW_HP -> event.getSourceName() + " low hp" + event.getValue();
            case HERO_DIED -> event.getSourceName() + "died";
            case BOSS_PHASE_CHANGED -> "Boss angry" + event.getValue();
            case BOSS_DEFEATED -> "Boss defeated!";
        };
        System.out.println("Battle log: " + ms );
    }
}
