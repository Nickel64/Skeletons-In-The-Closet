package Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Shlomoburg on 5/10/2017.
 */
public class EntitySet {

    private Image[] idle = new Image[4];
    private Image[][] attacks = new Image[4][6];
    Image defending;
    Image AoE;

    public EntitySet(boolean player, boolean boss, int enemyType){
        //if the entitySet belongs to a player
        try {
            if (player) {
                for (int n = 0; n < 4; n++) {
                    idle[n] = ImageIO.read(Resources.class.getResource("ImgResources/SpriteSets/Player/PlayerIdle" + n + ".png"));

                    //attacks
                    for(int i = 0; i < 6; i++){
                        attacks[n][i] = ImageIO.read(Resources.class.getResource("ImgResources/SpriteSets/Player/PlayerAttack" + n +"_"+ i + ".png"));
                    }
                }
                defending = ImageIO.read(Resources.class.getResource("ImgResources/SpriteSets/Player/shield.png"));
                AoE = ImageIO.read(Resources.class.getResource("ImgResources/SpriteSets/Player/AoE.png"));
            }
            //otherwise, it must be an enemy
            else if (boss) {
                if(enemyType == 99){
                    for (int n = 0; n < 4; n++) {
                        idle[n] = ImageIO.read(Resources.class.getResource("ImgResources/SpriteSets/Boss/FinalBoss/FinalBossIdle_" + n + ".png"));

                        //attacks
                        for(int i = 0; i < 6; i++){
                            attacks[n][i] = ImageIO.read(Resources.class.getResource("ImgResources/SpriteSets/Boss/FinalBossAttack" + n +"_"+ i + ".png"));
                        }
                    }
                }
                else {
                    for (int n = 0; n < 4; n++) {
                        idle[n] = ImageIO.read(Resources.class.getResource("ImgResources/SpriteSets/Boss/BossIdle_" + n + ".png"));

                        //attacks
                        for (int i = 0; i < 6; i++) {
                            attacks[n][i] = ImageIO.read(Resources.class.getResource("ImgResources/SpriteSets/Boss/BossAttack" + n + "_" + i + ".png"));
                        }
                    }
                }
            }
            else{   //regular enemy, need 2 or 3 variations
                Random random = new Random();
                int select = random.nextInt(3);
                //select that enemy for this entityset
                for (int n = 0; n < 4; n++) {
                    idle[n] = ImageIO.read(Resources.class.getResource("ImgResources/SpriteSets/Enemy/Enemy"+ select +"/EnemyIdle" + n + ".png"));

                    //attacks
                    for(int i = 0; i < 6; i++){
                        attacks[n][i] = ImageIO.read(Resources.class.getResource("ImgResources/SpriteSets/Enemy/Enemy"+select+"/EnemyAttack" + n + "_" + i + ".png"));
                    }
                }
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

    public Image getAoE(){
        return AoE;
    }

    public Image getAttack(int direction, int stage){
        return attacks[direction][stage];
    }
}
