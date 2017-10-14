package Utils;

import java.io.*;
import java.util.*;

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

        String saveName = new Date().toString();
        if(saves.keySet().contains(saveName)) saveName += " ";

        try {
            ByteArrayOutputStream fout = new ByteArrayOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(fout);

            oout.writeObject(m);
            oout.flush();

            saves.put(saveName, Base64.getEncoder().encodeToString(fout.toByteArray()));
            if(Resources.DEBUG) System.out.println(saveName + " saved!");

            return true;

        } catch(IOException e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Method will take a previously saved game, and load it
     */
    public Model load(String saveName){

        try {
            if(Resources.DEBUG) System.out.println("Loading: " + saveName);

            String str = saves.get(saveName);
            if(str == null) return null;

            byte[] dataIn = Base64.getDecoder().decode(str);
            ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(dataIn));

            Model newModel = (Model) oin.readObject();
            oin.close();
            newModel.resetGame();
            return newModel;

        } catch (Exception e){
            e.printStackTrace();
            throw new GameError(e.getMessage());
        }


    }

}
