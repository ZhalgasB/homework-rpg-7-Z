package com.narxoz.rpg.observer;

import java.util.Random;

public class LootDroper implements GameObserver{
    private final Random random = new Random();
    private final String[] phaseLoot = {"Ancient Rune", "Cursed Gem", "Dragon Scale"};
    private final String[] finalLoot = {"Legendary Sword", "Elixir of Immortality", "Boss Core"};

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED){
            String item = phaseLoot[random.nextInt(phaseLoot.length)];
            System.out.println("Loot: " + item);
        } else if (event.getType() == GameEventType.BOSS_DEFEATED) {
            String item = finalLoot[random.nextInt(finalLoot.length)];
            System.out.println("Loot: " + item);
        }
    }
}
