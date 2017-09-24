package Behaviour;

/**
 * The Enemy has been awoken, and is starting to get annoyed.
 * It's influence is getting larger and it is wanting to increase it's power!
 *
 * @author Morgan French-Stagg
 */
public class MildAnnoyanceStrategy implements BehaviourStrategy{
    public int determineDamageAmount() {
        return 3;
    }

    public double determineDamageProbability() {
        return 0.75;
    }
}
