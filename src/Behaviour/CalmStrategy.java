package Behaviour;

/**
 * For non attackey entities
 *
 * @author Morgan French-Stagg
 */
public class CalmStrategy implements BehaviourStrategy {
    @Override
    public int determineDamageAmount() {
        return 0;
    }

    @Override
    public double determineDamageProbability() {
        return 0;
    }
}
