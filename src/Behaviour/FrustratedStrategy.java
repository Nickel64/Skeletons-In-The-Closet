package Behaviour;

/**
 * If the Enemy is still struggling to defeat the Player,
 * it may need to become frustrated to finish it!
 *
 * @author Morgan French-Stagg
 */
public class FrustratedStrategy implements BehaviourStrategy {

    public int determineDamageAmount() {
        return 10;
    }

    public double determineDamageProbability() {
        return 1;
    }
}
