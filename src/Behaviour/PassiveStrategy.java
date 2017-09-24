package Behaviour;

/**
 * Used for Passive Enemies (mostly in a resting state).
 * These enemies are happy minding their own business.
 * So their first hits are not overly powerful.
 *
 * @author Morgan French-Stagg
 */
public class PassiveStrategy implements BehaviourStrategy{

    public int determineDamageAmount() {
        return 1;
    }

    public double determineDamageProbability() {
        return 0.5;
    }
}
