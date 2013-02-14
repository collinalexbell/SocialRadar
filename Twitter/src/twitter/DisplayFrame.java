package twitter;

import javax.swing.BoxLayout;

public class DisplayFrame extends javax.swing.JFrame {
    public DisplayFrame(){
        this.setSize(1700, 900); //The window Dimensions
        getContentPane().setLayout(
        	    new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS)
        	);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setBounds(20, 20,950, 700);
        processing.core.PApplet sketch = new Twitter();
        panel.add(sketch);
        this.add(panel);
        sketch.init(); //this is the function used to start the execution of the sketch
        this.setVisible(true);
    }

}
