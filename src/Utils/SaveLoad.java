package Utils;

import java.util.ArrayList;
import java.util.List;
import Model.*;

/**
 * Used to store and load game states
 *
 * @author Morgan French-Stagg
 */
public class SaveLoad {

    public List<String> saves; //used to keep a track of all previous saved files


    public void SaveLoad(){
        this.saves = new ArrayList<>();
    }

    /**
     * Method will Take a Model Object, and Serialise it then save to a file
     */
    public static boolean save(Model m){
        return false;
    }

    /**
     * Method will take a previously saved game, and load it
     */
    public void load(String saveName){

    }

}
