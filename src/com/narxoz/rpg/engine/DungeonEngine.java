package com.narxoz.rpg.engine;

import com.narxoz.rpg.boss.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.observer.EventPublisher;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DungeonEngine {
    private final List<Hero> heroes;
    private final DungeonBoss boss;
    private final EventPublisher publisher;
    private int roundsPlayed = 0;
    private static final int maxRounds = 50;

    public DungeonEngine(List<Hero> heroes, DungeonBoss boss, EventPublisher publisher) {
        this.heroes = heroes;
        this.boss = boss;
        this.publisher = publisher;
    }

    public EncounterResult runEncounter() {
        Set<String> lowHpNotified = new HashSet<>();

        while (boss.isAlive() && hasLivingHeroes() && roundsPlayed < maxRounds) {
            roundsPlayed++;

            for (Hero hero : heroes) {
                if (!hero.isAlive()) continue;

                int rawDmg = hero.getStrategy().calculateDamage(hero.getAttackPower());
                int bossDef = boss.getStrategy().calculateDefense(boss.getDefence());
                int dmg = Math.max(1, rawDmg - bossDef);

                boss.TakeDmg(dmg);

                publisher.notifyObserver(new GameEvent(GameEventType.ATTACK_LANDED, hero.getName(), dmg));
            }

            if (boss.isAlive()) {
                for (Hero hero : heroes) {
                    if (!hero.isAlive()) continue;

                    int rawDmg = boss.getStrategy().calculateDamage(boss.getAttPoer());
                    int heroD = hero.getStrategy().calculateDefense(hero.getDefense());
                    int dmg = Math.max(1, rawDmg - heroD);

                    hero.takeDamage(dmg);

                    publisher.notifyObserver(new GameEvent(GameEventType.ATTACK_LANDED, boss.getName(), dmg));

                    if (!hero.isAlive()) {
                        publisher.notifyObserver(new GameEvent(GameEventType.HERO_DIED, hero.getName(), 0));
                    } else if (hero.getHp() <= (hero.getMaxHp() * 3 / 10) && !lowHpNotified.contains(hero.getName())) {
                        publisher.notifyObserver(new GameEvent(GameEventType.HERO_LOW_HP, hero.getName(), hero.getHp()));
                        lowHpNotified.add(hero.getName());
                    }

                }


            }
        }

        boolean HeroesWon = !boss.isAlive();
        int survivingHeroes = 0;

        for (Hero hero : heroes) {
            if (hero.isAlive()) {
                survivingHeroes++;
            }
        }

        return new EncounterResult(HeroesWon,roundsPlayed,survivingHeroes);
    }
    private boolean hasLivingHeroes() {
        for (Hero hero : heroes) {
            if (hero.isAlive()) {
                return true;
            }
        }
        return false;    }
}
