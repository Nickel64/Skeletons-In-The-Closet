package Main;
import View.*;
import Model.*;

import javax.swing.*;

public class SkeletonsInTheCloset {

    public static void main(String[] args){
        SwingUtilities.invokeLater(()->new View());
        Model m = new Model();
        m.initialise();
    }
}
