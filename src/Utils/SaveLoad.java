package Utils;

import java.io.*;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import Model.*;

/**
 * Used to store and load game states
 *
 * @author Morgan French-Stagg
 */
public class SaveLoad {

    public HashMap<String, String> saves; //used to keep a track of all previous saved files

    DateFormat dateFormatUserFriendly = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    DateFormat dateFormatFile = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");


    public SaveLoad(){
        this.saves = new HashMap<>();
        File folder = new File(Paths.get("").toAbsolutePath().toString());
        File[] listOfFiles = folder.listFiles();

        for (File file: listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".sav")) {
                try {
                    String content = new Scanner(file).useDelimiter("\\Z").next();
                    saves.put(dateFormatUserFriendly.format(dateFormatFile.parse(file.getName())), content);
                }
                catch (Exception e){
                }
            }
        }
    }

    /**
     * Method will Take a Model Object, and Serialise it then save to a file
     */
    public boolean save(Model m){
        if(m == null) return false;

        Date now = Calendar.getInstance().getTime();

        String saveName = dateFormatUserFriendly.format(now);

        if(saves.keySet().contains(saveName)) saveName += " ";

        try {
            ByteArrayOutputStream fout = new ByteArrayOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(fout);

            oout.writeObject(m);
            oout.flush();

            String saveStr = Base64.getEncoder().encodeToString(fout.toByteArray());

            //write to file
            BufferedWriter writer = new BufferedWriter(new FileWriter(dateFormatFile.format(now) + ".sav"));
            writer.write(saveStr);
            writer.close();

            //write to internal save cache
            saves.put(saveName, saveStr);

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
            return null;
        }


    }

}
