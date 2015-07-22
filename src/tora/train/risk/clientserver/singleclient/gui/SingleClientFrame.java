package tora.train.risk.clientserver.singleclient.gui;

import tora.train.risk.clientserver.clientapp.ClientAppViewInterface;
import tora.train.risk.clientserver.utils.Helpers;

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
    private JButton readyButton;

    private JLabel statusLabel;
    private JTextArea incomingTextArea;
    private JTextField outgoingTextField;
    private String name;

    private JComboBox<String> playersCombo;

    public SingleClientFrame(String name){
        frame=new JFrame("Client [" + name + "]");

        this.name=name;
        JPanel backgroundPanel=new JPanel();
        backgroundPanel.setLayout(new GridLayout(5,0));
        backgroundPanel.setBackground(Color.darkGray);

        JPanel titlePanel= Helpers.buildTitlePanel("Welcome " + name);
        JPanel outgoingMsgPanel=buildMessageEditPanel();
        JPanel connectionPanel=buildConnectionPanel();
        JPanel incomingMsgPanel=buildIncomingMessagePanel();
        JPanel playersPanel=buildPlayersPanel();

        backgroundPanel.add(titlePanel);
        backgroundPanel.add(connectionPanel);
        backgroundPanel.add(playersPanel);
        backgroundPanel.add(outgoingMsgPanel);
        backgroundPanel.add(incomingMsgPanel);

        frame.getContentPane().add(backgroundPanel);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

    public void setReadyButtonListener(ActionListener a){
        this.readyButton.addActionListener(a);
    }

    /************************************************************************************
     * PANEL BUILDERS
     ***********************************************************************************/
    private JPanel buildMessageEditPanel(){
        JPanel panel=Helpers.buildCustomizedPanel("Outgoing", BoxLayout.X_AXIS);

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
        JPanel panel=Helpers.buildCustomizedPanel("Connection", BoxLayout.X_AXIS);

        connectButton=new JButton("Connect");
        disconnectButton=new JButton("Disconnect");
        readyButton=new JButton("Ready");

        statusLabel=new JLabel("Unknown");
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Status");
        titledBorder.setTitleColor(Color.WHITE);
        statusLabel.setBorder(titledBorder);
        statusLabel.setForeground(Color.RED);

        panel.add(Box.createRigidArea(new Dimension(30, 0)));
        panel.add(connectButton);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        panel.add(readyButton);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        panel.add(disconnectButton);
        panel.add(Box.createRigidArea(new Dimension(30, 0)));
        panel.add(statusLabel);

        return panel;
    }

    private JPanel buildPlayersPanel(){
        JPanel panel=Helpers.buildCustomizedPanel("Players", BoxLayout.X_AXIS);

        playersCombo=new JComboBox<String>();
        playersCombo.setForeground(SingleClientViewInterface.PURPLE);

        JPanel inner=new JPanel();
        inner.setOpaque(false);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.add(Box.createRigidArea(new Dimension(0, 30)));
        inner.add(playersCombo);
        inner.add(Box.createRigidArea(new Dimension(0, 30)));

        panel.add(Box.createRigidArea(new Dimension(100,0)));
        panel.add(inner);
        panel.add(Box.createRigidArea(new Dimension(100,00)));

        return panel;
    }

    private JPanel buildIncomingMessagePanel(){
        JPanel panel=Helpers.buildCustomizedPanel("Incoming", BoxLayout.X_AXIS);

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
    /**
     * Sets the text in the statusLabel
     *
     * @param isConnected boolean value telling whether the text on the label should be set to "Connected" or
     *                    "Disconnected"
     */
    public void setStatus(boolean isConnected){
        this.statusLabel.setText(isConnected ? "Connected" : "Not connected");
    }

    /**
     * Appends some text to the incomingTextArea
     *
     * @param s the String to be appended
     */
    public void setIncomingAreaText(String s) {
        this.incomingTextArea.append(s+"\n");
    }

    /**
     * Reads the text from the outgoingTextField and returns it
     *
     * @return  the text on the outgoingTextField
     */
    public String getOutgoingMessageFromField(){
        return this.outgoingTextField.getText();
    }

    /**
     * Closes the frame
     */
    public void close(){
        this.frame.dispose();
    }

    /**
     *
     * @return the name of the frame as given when created
     */
    public String getName() {
        return name;
    }

    /**
     * Displays a new option panel for informative purposes
     *
     * @param s the String to be displayed on the option panel
     */
    public void showOptionPanel(String s) {
        JOptionPane.showMessageDialog(frame, s);
    }

    /**
     * Adds a new String to the playersCombo
     *
     * @param player the String (name of the player) to be added
     */
    public void addPlayer(String player){
        this.playersCombo.addItem(player);
    }

    /**
     * Removes a String from the playersCombo
     *
     * @param player the String (name of the player) to be removed
     */
    public void removePlayer(String player){
        this.playersCombo.removeItem(player);
    }

    /**
     * Created by intern on 7/22/15.
     */
    public static class MapView {

    }
}
