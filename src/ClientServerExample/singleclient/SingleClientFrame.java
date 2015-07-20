package ClientServerExample.singleclient;

import ClientServerExample.clientapp.ClientAppViewInterface;
import ClientServerExample.utils.Helpers;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Client Side GUI window, i.e. the window that opens for each individual client.
 *
 * Created by Andrea on 7/16/2015.
 */
public class SingleClientFrame implements SingleClientViewInterface {
    private JFrame frame;
    private JButton connectButton;
    private JButton disconnectButton;
    private JButton sendMessageButton;
    private JLabel statusLabel;
    private JTextArea incomingTextArea;
    private JTextField outgoingTextField;

    public SingleClientFrame(String name){
        frame=new JFrame("Client");

        JPanel backgroundPanel=new JPanel();
        backgroundPanel.setLayout(new GridLayout(4,0));
        backgroundPanel.setBackground(Color.darkGray);

        JPanel titlePanel= Helpers.buildTitlePanel("Welcome " + name);
        JPanel outgoingMsgPanel=buildMessageEditPanel();
        JPanel connectionPanel=buildConnectionPanel();
        JPanel incomingMsgPanel=buildIncomingMessagePanel();

        backgroundPanel.add(titlePanel);
        backgroundPanel.add(connectionPanel);
        backgroundPanel.add(outgoingMsgPanel);
        backgroundPanel.add(incomingMsgPanel);

        frame.getContentPane().add(backgroundPanel);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setSize(SingleClientViewInterface.WINDOW_X, SingleClientViewInterface.WINDOW_Y);
    }

    /*************************************************************************************
     * LISTENERS
     ************************************************************************************/
    public void setConnectionButtonListener(ActionListener a){
        this.connectButton.addActionListener(a);
    }

    public void setDisconnectButtonListener(ActionListener a){
        this.disconnectButton.addActionListener(a);
    }

    public void setSendMessageButtonListener(ActionListener a){
        this.sendMessageButton.addActionListener(a);
    }

    /************************************************************************************
     * PANEL BUILDERS
     ***********************************************************************************/
    private JPanel buildMessageEditPanel(){
        JPanel panel=Helpers.buildCustomizedPanel("Outgoing");

        outgoingTextField=new JTextField();
        outgoingTextField.setBackground(Color.WHITE);
        outgoingTextField.setForeground(ClientAppViewInterface.PURPLE);
        outgoingTextField.setEditable(true);

        sendMessageButton=new JButton("Send Message");

        panel.add(outgoingTextField);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        panel.add(sendMessageButton);
        return panel;
    }

    private JPanel buildConnectionPanel(){
        JPanel panel=Helpers.buildCustomizedPanel("Connection");

        connectButton=new JButton("Connect");
        disconnectButton=new JButton("Disconnect");

        statusLabel=new JLabel("Unknown");
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Status");
        titledBorder.setTitleColor(Color.WHITE);
        statusLabel.setBorder(titledBorder);
        statusLabel.setForeground(Color.RED);

        panel.add(Box.createRigidArea(new Dimension(50, 0)));
        panel.add(connectButton);
        panel.add(Box.createRigidArea(new Dimension(100, 0)));
        panel.add(disconnectButton);
        panel.add(Box.createRigidArea(new Dimension(50, 0)));
        panel.add(statusLabel);

        return panel;
    }

    private JPanel buildIncomingMessagePanel(){
        JPanel panel=Helpers.buildCustomizedPanel("Incoming");

        incomingTextArea=new JTextArea(10,10);
        incomingTextArea.setBackground(Color.WHITE);
        incomingTextArea.setForeground(SingleClientViewInterface.PURPLE);
        incomingTextArea.setEditable(false);

        JScrollPane scrollPane=new JScrollPane(incomingTextArea);

        panel.add(scrollPane);
        return panel;
    }

    /************************************************************************************
     * Methods that interrogate/modify/act on JComponents
     ***********************************************************************************/

    public void setStatus(boolean isConnected){
        this.statusLabel.setText(isConnected ? "Connected" : "Not connected");
    }

    public void setIncomingAreaText(String s) {
        this.incomingTextArea.append("\n"+s);
    }

    public String getOutgoingMessageFromField(){
        return this.outgoingTextField.getText();
    }

    public void close(){
        this.frame.dispose();
    }
}
