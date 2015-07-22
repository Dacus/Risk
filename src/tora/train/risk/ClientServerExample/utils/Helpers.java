package tora.train.risk.ClientServerExample.utils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by Andrea on 7/16/2015.
 */
public class Helpers {

    public static JPanel buildCustomizedPanel(String name, int direction){
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel, direction));
        panel.setOpaque(false);
        panel.setForeground(Color.WHITE);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(name);
        titledBorder.setTitleColor(Color.WHITE);
        panel.setBorder(titledBorder);
        return panel;
    }

    public static JPanel buildTitlePanel(String title){
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);
        panel.setForeground(Color.WHITE);

        JLabel titleLabel=new JLabel(title);
        titleLabel.setForeground(Color.white);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        panel.add(Box.createRigidArea(new Dimension(10,0)));
        panel.add(titleLabel);

        return panel;
    }
}
