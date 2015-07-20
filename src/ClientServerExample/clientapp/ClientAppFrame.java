package ClientServerExample.clientapp;

import ClientServerExample.utils.Helpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Andrea on 7/17/2015.
 *
 * Application GUI for the client machine.
 */
public class ClientAppFrame implements ClientAppViewInterface{
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
        frame.setSize(ClientAppViewInterface.WINDOW_X, ClientAppViewInterface.WINDOW_Y);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private JPanel buildNewClientPanel(){
        JPanel panel= Helpers.buildCustomizedPanel("Client name");

        newClientButton=new JButton("New Client");
        nameField=new JTextField();
        nameField.setFont(new Font("Arial", Font.BOLD, 16));
        nameField.setForeground(ClientAppViewInterface.PURPLE);

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
