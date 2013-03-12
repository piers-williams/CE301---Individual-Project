package Project.NLP;

import Project.Game.AI.SPL.Orders.SPLObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class UI {

    public static void main(String[] args) {
        JFrame frame = new JFrame("NLP UI");
        final JTextField field = new JTextField();
        field.setSize(new Dimension(500, 50));
        JButton button = new JButton("Process");

        frame.setPreferredSize(new Dimension(500, 100));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pipeline pipeline = new Pipeline();

                SPLObject object = pipeline.process(field.getText());
                System.out.println(object);
            }
        });

        frame.setLayout(new GridLayout(2, 0));
        frame.add(field);
        frame.add(button);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
