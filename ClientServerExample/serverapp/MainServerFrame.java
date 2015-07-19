package serverapp;

import utils.Helpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Server side GUI.
 *
 * Created by Andrea on 7/16/2015.
 */
public class MainServerFrame implements MainServerViewInterface {
    private JFrame frame;
    private JTextArea incomingTextArea;
    private JTextField outgoingTextField;
    private JButton sendMessageButton;
    private JButton stopServerButton;
    private JLabel onlineClientsLabel;

    public MainServerFrame() {
        frame = new JFrame("Server");

        JPanel backgroundPanel=new JPanel();
        backgroundPanel.setLayout(new GridLayout(5,0));
        backgroundPanel.setBackground(Color.darkGray);

        JPanel titlePanel= Helpers.buildTitlePanel("Server");
        JPanel statusPanel=buildStatusPanel();
        JPanel outgoingMsgPanel=buildOutgoingMessagePanel();
        JPanel incomingMsgPanel=buildIncomingMessagePanel();
        JPanel buttonPanel=buildButtonPanel();

        backgroundPanel.add(titlePanel);
        backgroundPanel.add(statusPanel);
        backgroundPanel.add(incomingMsgPanel);
        backgroundPanel.add(outgoingMsgPanel);
        backgroundPanel.add(buttonPanel);

        frame.getContentPane().add(backgroundPanel);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setSize(MainServerViewInterface.WINDOW_X, MainServerViewInterface.WINDOW_Y);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /************************************************************************************
     * PANEL BUILDERS
     ***********************************************************************************/
    private JPanel buildOutgoingMessagePanel(){
        JPanel panel=Helpers.buildCustomizedPanel("Outgoing");

        outgoingTextField=new JTextField(10);
        outgoingTextField.setBackground(Color.WHITE);
        outgoingTextField.setForeground(new Color(125, 5, 82));

        sendMessageButton=new JButton("Send To All");

        panel.add(outgoingTextField);
        panel.add(Box.createRigidArea(new Dimension(30, 0)));
        panel.add(sendMessageButton);

        return panel;
    }

    private JPanel buildIncomingMessagePanel(){
        JPanel panel=Helpers.buildCustomizedPanel("Incoming");

        incomingTextArea=new JTextArea(10,10);
        incomingTextArea.setBackground(Color.WHITE);
        incomingTextArea.setForeground(new Color(125, 5, 82));
        incomingTextArea.setEditable(false);

        JScrollPane scrollPane=new JScrollPane(incomingTextArea);
        panel.add(scrollPane);

        return panel;
    }

    private JPanel buildButtonPanel(){
        JPanel panel=Helpers.buildCustomizedPanel("Buttons");

        stopServerButton=new JButton("Stop Server");

        panel.add(Box.createRigidArea(new Dimension(100, 0)));
        panel.add(stopServerButton);

        return panel;
    }

    private JPanel buildStatusPanel(){
        JPanel panel=Helpers.buildCustomizedPanel("Status");

        JPanel innerPanel=new JPanel();
        innerPanel.setOpaque(false);
        innerPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints=new GridBagConstraints();

        JLabel label1=new JLabel("Clients online: ");
        label1.setForeground(Color.WHITE);
        constraints.gridx=0;
        constraints.gridy=0;
        innerPanel.add(label1, constraints);

        onlineClientsLabel=new JLabel("0");
        onlineClientsLabel.setForeground(Color.RED);
        constraints.gridx=1;
        constraints.gridy=0;
        innerPanel.add(onlineClientsLabel, constraints);

        panel.add(innerPanel);
        return panel;
    }

    /************************************************************************************
     * Methods that interrogate/modify/act on JComponents
     ***********************************************************************************/

    public void setIncomingAreaText(String str){
        this.incomingTextArea.append("\n"+str);
    }

    public String getOutgoingTextField(){
        return this.outgoingTextField.getText();
    }

    public void close(){
        frame.dispose();
    }

    /*************************************************************************************
     * LISTENERS
     ***********************************************************************************/
    public void setQuitButtonListener(ActionListener a) {
        this.stopServerButton.addActionListener(a);
    }

    public void setSendButtonListener(ActionListener a) {
        this.sendMessageButton.addActionListener(a);
    }

    public void changeNumberOfClientsOnline(int i){
        this.onlineClientsLabel.setText(String.valueOf(i));
    }
}
