package Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import Model.*;

/**
 * Used to store and load game states
 *
 * @author Morgan French-Stagg
 */
public class SaveLoad {

    public HashMap<String, String> saves; //used to keep a track of all previous saved files


    public SaveLoad(){
        this.saves = new HashMap<>();
    }

    /**
     * Method will Take a Model Object, and Serialise it then save to a file
     */
    public boolean save(Model m){
        if(m == null) return false;

        StringBuffer serialised = new StringBuffer();

        serialised.append(m.serialise());
        String saveName = new Date().toString();
        if(Resources.DEBUG) System.out.println(saveName + " saved!");
        saves.put(saveName, serialised.toString());

        return true;
    }

    /**
     * Method will take a previously saved game, and load it
     */
    public Model load(String saveName){

        if(Resources.DEBUG) System.out.println("Loading: " + saveName);
        saves.get(saveName);

        Model newModel = new Model();
        try{
            newModel.initialise();
        } catch (IOException e){
            e.printStackTrace();
        }

        return newModel;
    }

}
