package com.narxoz.rpg;

import com.narxoz.rpg.boss.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.engine.DungeonEngine;
import com.narxoz.rpg.engine.EncounterResult;
import com.narxoz.rpg.observer.*;
import com.narxoz.rpg.strategy.AggressiveHeroStrategy;
import com.narxoz.rpg.strategy.BalancedHeroStrategy;
import com.narxoz.rpg.strategy.DefensiveHeroStrategy;

import java.util.List;

/**
 * Entry point for Homework 7 — The Cursed Dungeon: Boss Encounter System.
 *
 * Build your heroes, boss, observers, and engine here, then run the encounter.
 */
public class Main {

    public static void main(String[] args) {
        // TODO (student): Create at least 3 heroes with different combat strategies
        Hero hero1 = new Hero("Lelouch",120,35,15,new AggressiveHeroStrategy());
        Hero hero2 = new Hero("CC",100,28,25,new DefensiveHeroStrategy());
        Hero hero3 = new Hero("Disctra", 110, 32, 18, new BalancedHeroStrategy());
        List<Hero> heroes = List.of(hero1,hero2,hero3);
        // TODO (student): Create a DungeonBoss with meaningful stats

        EventPublisher publisher = new EventPublisher();

        DungeonBoss boss = new DungeonBoss(450,40,20,publisher,20);
        // TODO (student): Instantiate and register all 5 observers
        BattleLogger logger = new BattleLogger();
        Achievement achievement = new Achievement();
        Support support = new Support(heroes);
        HeroStatus status = new HeroStatus(heroes);
        LootDroper droper = new LootDroper();

        publisher.registerObserver(logger);
        publisher.registerObserver(achievement);
        publisher.registerObserver(support);
        publisher.registerObserver(status);
        publisher.registerObserver(droper);
        publisher.registerObserver(boss);

        // TODO (student): Create a DungeonEngine and run the encounter

        DungeonEngine engine = new DungeonEngine(heroes,boss,publisher);
        EncounterResult result = engine.runEncounter();
        System.out.println("Lelouch switches strategy to Defensive  ");
        hero1.setStrategy(new DefensiveHeroStrategy());
        // TODO (student): Print the EncounterResult at the end
        System.out.println("Result: ");
        System.out.println("Heroes Won: " + result.isHeroesWon());
        System.out.println("Rounds: " + result.getRoundsPlayed());
        System.out.println("Surviving heroes: "+ result.getSurvivingHeroes());

        System.out.println("END");
    }
}
