package tora.train.risk.clientserver.clientapp;

import tora.train.risk.clientserver.utils.SwingHelpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Andrea on 7/17/2015.
 *
 * Application GUI for the client machine.
 */
public class ClientAppFrame {
    private static final int WINDOW_X=300;
    private static final int WINDOW_Y=100;
    private static final Color PURPLE=new Color(125, 5, 82);

    private JFrame frame;
    private JButton newClientButton;
    private JTextField nameField;

    public ClientAppFrame(){

        this.frame=new JFrame("Client GUI");

        JPanel backgroundPanel=new JPanel();
        backgroundPanel.setBackground(Color.darkGray);
        backgroundPanel.setLayout(new GridLayout(1, 1));

        JPanel namePanel=buildNewClientPanel();

        backgroundPanel.add(namePanel);

        frame.getContentPane().add(backgroundPanel);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setSize(WINDOW_X, WINDOW_Y);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private JPanel buildNewClientPanel(){
        JPanel panel= SwingHelpers.buildCustomizedPanel("Client name", BoxLayout.X_AXIS);

        newClientButton=new JButton("New Client");
        nameField=new JTextField();
        nameField.setFont(new Font("Arial", Font.BOLD, 16));
        nameField.setForeground(PURPLE);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(nameField);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(newClientButton);
        return panel;
    }

    /*************************************************************************
     * LISTENERS
     ************************************************************************/
    public void setNewClientButtonListener(ActionListener a){
        this.newClientButton.addActionListener(a);
    }

    /*************************************************************************
     * Methods that interrogate/modify/act on JComponents
     ************************************************************************/

    public String getClientName(){
        return nameField.getText();
    }

    public void clearNameField(){
        this.nameField.setText("");
    }

    public void showOptionPanel(String s) {
        JOptionPane.showMessageDialog(frame, s);
    }
}
