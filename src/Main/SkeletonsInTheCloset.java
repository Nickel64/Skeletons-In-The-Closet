package Main;
import View.*;
import Model.*;

import javax.swing.*;

public class SkeletonsInTheCloset {

    public static void main(String[] args){
        Model m = new Model();
        m.initialise();
        SwingUtilities.invokeLater(()->new View(m));
    }
}
