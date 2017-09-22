package Behaviour;

/**
 * The Enemy is ANGRY! It is determined to kill the Player now
 *
 * @author Morgan French-Stagg
 */
public class AngerStrategy implements BehaviourStrategy{
    public int determineDamageAmount() {
        return 8;
    }

    public double determineDamageProbability() {
        return 0.9;
    }
}
