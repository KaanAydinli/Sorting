
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class SortingFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        SortingPanel panel = new SortingPanel();
        JMenuItem menuitem1 = new JMenuItem("Bubble");
        JMenuItem menuitem2 = new JMenuItem("Insertion");
        JMenuItem menuitem3 = new JMenuItem("Selection");
        JMenuItem menuitem4 = new JMenuItem("Merge");
        JMenuItem menuitem5 = new JMenuItem("Quick");
        JSlider slider = new JSlider();
        JSlider slider2 = new JSlider();
        JLabel label = new JLabel("Speed:");
        JLabel label2 = new JLabel("Size:");
        JLabel speedLabel = new JLabel();
        menuBar.add(label);
        menuBar.add(slider);
        menuBar.add(label2);
        menuBar.add(slider2);
        menuBar.add(speedLabel);

        slider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if(slider.getValue() != 0){
                    panel.speed = slider.getValue();
                    
                }
                
            }
            
        });
        slider2.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if(slider2.getValue() != 0 && panel.isSorted){
                    panel.arraySize = slider2.getValue() * 10;
                    panel.shuffle();
                    speedLabel.setText(slider2.getValue() * 10 + "");
                    System.out.println(panel.arraySize);
                }
            }
            
        });
        menuitem1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                    panel.state = 0;
                    panel.shuffle();
                    panel.isSorted = false;
                    panel.resetEverything();
                           
            }
            
        });
        menuitem2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                    panel.state = 1;
                    panel.shuffle();
                    panel.isSorted = false;
                    panel.resetEverything();
                
                
            }
            
        });
        menuitem3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                    panel.state = 2;
                    panel.shuffle();
                    panel.isSorted = false;
                    panel.resetEverything();
                
                
            }
            
        });
        menuitem4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                    panel.state = 3;
                    panel.shuffle();
                    panel.isSorted = false;
                    panel.resetEverything();
                    
                
            }
            
        });
        menuitem5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                    panel.state = 4;
                    panel.shuffle();
                    panel.isSorted = false;
                    panel.resetEverything();
                    panel.start = System.nanoTime();
                
            }
        });

      

        menuBar.add(menuitem1);
        menuBar.add(menuitem2);
        menuBar.add(menuitem3);
        menuBar.add(menuitem4);
        menuBar.add(menuitem5);
        frame.setJMenuBar(menuBar);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(100,100);
        frame.pack();
        frame.setVisible(true);
    }
}
