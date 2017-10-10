package Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Created by Shlomoburg on 5/10/2017.
 */
public class EntitySet {

    private Image[] idle = new Image[4];
    private Image[][] movements;
    private Image[][] attacks = new Image[4][6];
    Image defending;
    Image AoE;

    public EntitySet(boolean player, int enemyType){
        //if the entitySet belongs to a player
        try {
            if (player) {
                for (int n = 0; n < 4; n++) {
                    idle[n] = ImageIO.read(Resources.class.getResource("ImgResources/SpriteSets/Player/PlayerIdle" + n + ".png"));

                    //walks and attacks
                    //for(int i = 0; i < 6; i++){
                        //attacks[n][i] = ImageIO.read(Resources.class.getResource("ImgResources/SpriteSets/Player/PlayerAttack" +2+"_"+ i + ".png"));
                   // }
                }
                defending = ImageIO.read(Resources.class.getResource("ImgResources/SpriteSets/Player/shield.png"));
                AoE = ImageIO.read(Resources.class.getResource("ImgResources/SpriteSets/Player/AoE.png"));
            }
            //otherwise, it must be an enemy
            else {

            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public Image getIdle(int direction){
        return idle[direction];
    }

    public Image getDefending(){return defending;}

    public Image[] getMoveSequence(int direction){
        return movements[direction];
    }

    public Image[] getAttackSequence(int direction){
        return attacks[direction];
    }
}
