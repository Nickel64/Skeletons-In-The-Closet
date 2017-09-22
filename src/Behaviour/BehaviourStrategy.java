package Behaviour;

import Entities.Entity;

/**
 * BehaviourStrategy is used for Enemies to determine
 * their attacks based off their current anger
 *
 * @author Morgan French-Stagg
 */
public interface BehaviourStrategy {
    public int determineDamageAmount();
    public double determineDamageProbability();

    default void attemptAttack(Entity other) {
        if(other == null) return;

        if(Math.random() < determineDamageProbability()){
            other.damaged(determineDamageAmount());
        }
    }
}
