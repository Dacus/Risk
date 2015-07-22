package tora.train.risk.GUI;

import tora.train.risk.ArenaController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginView {

    private JFrame frame;
    private JTextField textFieldPlayersNr;
    private JLabel lblHowManyPlayers;
    private JLabel lblInsertNames;
    private JLabel statusBar;
    private JButton btnSubmit;
    private JButton btnAddPlayers;
    private ArrayList<JTextField> listOfTextFields;


    public void setStatusBar(String statusBar) {
        this.statusBar.setText(statusBar);
    }

    public void newListOfTextFields() {
        this.listOfTextFields = new ArrayList<JTextField>();
    }

    public void addTextFieldInList(JTextField tf){
        listOfTextFields.add(tf);
    }

    public JFrame getFrame() {
        return frame;
    }

    /**
     * Daca returnezi numarul de playeri, returneaza un Integer, nu un JTextField pe care sa fie obligat clientul
     * sa apelze getText() iar apoi sa parseze. Don't Repeat Yourself
     */
    public JTextField getTextFieldPlayersNr() {
        return textFieldPlayersNr;
    }

    /**
     * Prefera sa oferi o metoda 'setStatus(String status)'. In felul acesta poti modifica felul in care statusul
     * e afisat in intefata fara sa modifici codul care seteaza statusul. In plus, nu oferi gratuit acces la field-urile
     * clasei.
     */
    public JLabel getStatusBar() {
        return statusBar;
    }

    /**
     * Metodele astea nu sunt necesare.
     */
    public JButton getBtnSubmit() {
        return btnSubmit;
    }

    public JButton getBtnAddPlayers() {
        return btnAddPlayers;
    }

    /**
     * 1. Ce reprezinta text field-urile acestea? Metoda nu-mi comunica nimic.
     * 2. Evita sa returnezi componente swing daca ai nevoie doar de continutul lor. Prefera
     * o metoda: 'getPlayerNames'
     */
    public ArrayList<JTextField> getListOfTextFields() {
        return listOfTextFields;
    }

    /**
     * Nu folosi comentarii gratuite decat daca esti obligat de conventiile echipei.
     * 1. Cauta un nume potrivit. Daca ai nevoie de comentariu pe o zona de cod, extrage o metoda.
     * 2. Daca ai nevoie de comentariu la un field / variabila, gandeste-te de ce numele variabilei nu comunica
     * suficienta informatie.
     * 3. Comentariile utile sunt acelea care explica metodele publice.
     */
    /**
     * Create the application.
     */
    public LoginView() {
        StaticInformations.setArenaController(new ArenaController());
        initialize();
    }
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        frame = new JFrame("Welcome!");
        frame.setResizable(false);
        frame.setBounds(100, 100, 450, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        statusBar = new JLabel("");
        statusBar.setBounds(12, 450, 413, 19);
        frame.getContentPane().add(statusBar);

        textFieldPlayersNr = new JTextField();
        textFieldPlayersNr.setBounds(221, 36, 114, 19);
        frame.getContentPane().add(textFieldPlayersNr);
        textFieldPlayersNr.setColumns(10);

        lblHowManyPlayers = new JLabel("How many players?");
        lblHowManyPlayers.setBounds(50, 38, 153, 15);
        frame.getContentPane().add(lblHowManyPlayers);

        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(138, 91, 117, 25);
        frame.getContentPane().add(btnSubmit);

        lblInsertNames = new JLabel("Please insert the players name");
        lblInsertNames.setHorizontalAlignment(SwingConstants.CENTER);
        lblInsertNames.setBounds(50, 96, 285, 15);
        lblInsertNames.setVisible(true);
        frame.getContentPane().add(lblInsertNames);

        btnAddPlayers = new JButton("Add players");
        btnAddPlayers.setVisible(true);
        btnAddPlayers.setBounds(138, 416, 126, 25);
        frame.getContentPane().add(btnAddPlayers);


        firstView();
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public void addComponent(Container c){
        frame.getContentPane().add(c);
    }

    /**
     * 1. Ce inseamna firstView? dar second?
     * 2. Metodele firstView si secondView nu returneaza nimic, deci nu ofera acces la vreun view. Presupun ca
     * modifica starea interna a obiectului. Ar trebui desci sa fie verbe (ca majoritatea metodelor) si sa exprime
     * ce anume vrei sa faca obiectul pentru tine. Numele 'showFirstView' e mai potrivit. show<nume_descriptiv>View
     * ar fi si mai potrivit.
     */
    public void firstView() {
        textFieldPlayersNr.setVisible(true);
        lblHowManyPlayers.setVisible(true);
        btnSubmit.setVisible(true);
        lblInsertNames.setVisible(false);
        btnAddPlayers.setVisible(false);
    }

    public void secondView() {
        textFieldPlayersNr.setVisible(false);
        lblHowManyPlayers.setVisible(false);
        btnSubmit.setVisible(false);
        lblInsertNames.setVisible(true);
        btnAddPlayers.setVisible(true);
    }

    /*************************************************************************
     * LISTENERS
     ************************************************************************/

    public void setBtnAddPlayersListener(ActionListener a) {
        this.btnAddPlayers.addActionListener(a);
    }

    public void setBtnSubmitListener(ActionListener a) {
        this.btnSubmit.addActionListener(a);
    }
}
