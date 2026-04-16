package com.narxoz.rpg.boss;

import com.narxoz.rpg.observer.EventPublisher;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;
import com.narxoz.rpg.strategy.BossPhase1Strategy;
import com.narxoz.rpg.strategy.BossPhase2Strategy;
import com.narxoz.rpg.strategy.BossPhase3Strategy;
import com.narxoz.rpg.strategy.CombatStrategy;

public class DungeonBoss implements GameObserver {
    private final String name = "Lord";
    private int hp;
    private final  int maxHp;
    private final int attPoer;
    private final int defence;
    private CombatStrategy strategy;

    private final CombatStrategy phase1 = new BossPhase1Strategy();
    private final CombatStrategy phase2 = new BossPhase2Strategy();
    private final CombatStrategy phase3 = new BossPhase3Strategy();

    private final EventPublisher publisher;

    public DungeonBoss(int hp,int maxHp, int attPoer, int defence, EventPublisher publisher) {
        this.hp = hp;
        this.maxHp = maxHp;
        this.attPoer = attPoer;
        this.defence = defence;
        this.publisher = publisher;
        this.strategy =phase1;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttPoer() {
        return attPoer;
    }

    public int getDefence() {
        return defence;
    }

    public CombatStrategy getStrategy() {
        return strategy;
    }

    public boolean isAlive(){
        return hp > 0;
    }

    public void TakeDmg(int amount){
        int oldHp = hp;
        hp = Math.max(0, hp - amount);

        checkPhase(oldHp);

        if (hp == 0 && oldHp > 0){
            publisher.notifyObserver(new GameEvent(GameEventType.BOSS_DEFEATED,name,0));
        }
    }

    private void checkPhase(int oldHp) {
        int OldPct = (oldHp *100) / maxHp;
        int newPct = (hp * 100) / maxHp;

        if (OldPct > 60 && newPct <= 60){
            publisher.notifyObserver(new GameEvent(GameEventType.BOSS_PHASE_CHANGED,name,2));

        }
        if (OldPct > 30 && newPct <= 30){
            publisher.notifyObserver(new GameEvent(GameEventType.BOSS_PHASE_CHANGED,name,3));

        }
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED){
            int phase = event.getValue();
            if (phase == 2){
                strategy = phase1;
            } else if (phase == 3) {
                strategy = phase3;
            }
        }

    }
}
