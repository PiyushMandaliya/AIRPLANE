package AirPlane.view;

import javax.swing.*;
import java.awt.*;

public class AirplaneLoadLogGUI extends JFrame {

    private TextArea textArea;

    public AirplaneLoadLogGUI(String message){
        setTitle("Airplane Load Log");
        setSize(400, 400);
        setLocationRelativeTo(null);    //Centered

        setLayout(new FlowLayout());
        textArea = new TextArea(30,40);
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        add(scroll);

        textArea.setText(message);


        scroll.setSize( 100, 100 );
        textArea.setBackground(Color.LIGHT_GRAY);

        setVisible(true);
    }

}
