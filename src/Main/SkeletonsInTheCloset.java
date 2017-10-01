package Main;
import View.*;
import Model.*;

import javax.swing.*;
import java.io.IOException;

public class SkeletonsInTheCloset {

    public static void main(String[] args) {
        Model m = new Model();
        try {
            m.initialise();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(()->new View(m));
    }
}
