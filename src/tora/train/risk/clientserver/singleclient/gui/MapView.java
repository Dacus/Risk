package tora.train.risk.clientserver.singleclient.gui;

import tora.train.risk.Arena;
import tora.train.risk.Player;
import tora.train.risk.Territory;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class MapView {
    private JFrame frame;
    private JLabel lblCurrentPlayer;
    private JTable tableArena;
    private DefaultTableModel model;
    private JLabel lblYourTerritories;
    private JTextArea textAreaCurrentPlayerTerritories;
    private JTextField textFieldXposSource;
    private JTextField textFieldYposSource;
    private JLabel labelParLeft;
    private JLabel labelParRight;
    private JTextField textFieldUnits;
    private JButton btnAddReinforcement;
    private JButton btnSubmitAllReinforcements;
    private JLabel lblReinforcementsLeft;
    private JLabel lblInsertDestPosition;
    private JLabel labelPositionDelimiterSource;
    private JLabel lblInsertSourcePosition;
    private JLabel labelParLeftD;
    private JLabel labelParRightD;
    private JLabel labelPositionDelimiterDestination;
    private JTextField textFieldXposDest;
    private JTextField textFieldYposDest;
    private JLabel lblInsertUnits;
    private JButton btnAddAttack;


    /**
     * Create the frame.
     */
    public MapView(String title) {
        initializeFrame(title);
        buildReinforcePhaseView();
        buildAttackPhaseView();
        reinforcePhaseView();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initializeFrame(String title) {
        frame = new JFrame(title);
        frame.setBounds(100, 100, 900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblPlayer = new JLabel("Player: ");
        lblPlayer.setBounds(50, 20, 125, 25);
        frame.getContentPane().add(lblPlayer);

        lblCurrentPlayer = new JLabel("");
        lblCurrentPlayer.setBounds(150, 20, 125, 25);
        frame.getContentPane().add(lblCurrentPlayer);

        lblReinforcementsLeft = new JLabel("~");
        lblReinforcementsLeft.setBounds(350, 20, 125, 25);
        frame.getContentPane().add(lblReinforcementsLeft);

        Object[] data = {" ", 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        model = new DefaultTableModel(0, 1);
        for (int i = 0; i < 11; i++) {
            model.addColumn(i, data);
        }

        tableArena = new JTable(model);
        tableArena.setEnabled(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableArena.setDefaultRenderer(Object.class, centerRenderer);
        tableArena.setBounds(12, 44, 876, 198);
        frame.getContentPane().add(tableArena);

        lblYourTerritories = new JLabel("Your territories are at positions:");
        lblYourTerritories.setBounds(12, 254, 242, 15);
        frame.getContentPane().add(lblYourTerritories);

        textAreaCurrentPlayerTerritories = new JTextArea();
        textAreaCurrentPlayerTerritories.setEditable(false);
        textAreaCurrentPlayerTerritories.setBounds(22, 275, 232, 265);
        frame.getContentPane().add(textAreaCurrentPlayerTerritories);
    }

    public void buildReinforcePhaseView(){
        textFieldUnits = new JTextField();
        textFieldUnits.setBounds(575, 304, 114, 19);
        frame.getContentPane().add(textFieldUnits);
        textFieldUnits.setColumns(10);

        btnAddReinforcement = new JButton("Add Reinforcement");
        btnAddReinforcement.setBounds(414, 450, 240, 25);
        frame.getContentPane().add(btnAddReinforcement);

        btnSubmitAllReinforcements = new JButton("Submit All Reinforcements");
        btnSubmitAllReinforcements.setBounds(414, 400, 240, 25);
        frame.getContentPane().add(btnSubmitAllReinforcements);

        lblInsertUnits = new JLabel("Insert units:");
        lblInsertUnits.setBounds(315, 300, 220, 15);
        frame.getContentPane().add(lblInsertUnits);

        textFieldXposDest = new JTextField();
        textFieldXposDest.setBounds(571, 254, 41, 19);
        frame.getContentPane().add(textFieldXposDest);
        textFieldXposDest.setColumns(10);

        textFieldYposDest = new JTextField();
        textFieldYposDest.setColumns(10);
        textFieldYposDest.setBounds(648, 254, 41, 19);
        frame.getContentPane().add(textFieldYposDest);


        labelPositionDelimiterDestination = new JLabel(":");
        labelPositionDelimiterDestination.setBounds(630, 256, 14, 15);
        frame.getContentPane().add(labelPositionDelimiterDestination);

        labelParLeftD = new JLabel("(");
        labelParLeftD.setFont(new Font("Dialog", Font.BOLD, 14));
        labelParLeftD.setBounds(558, 249, 21, 25);
        frame.getContentPane().add(labelParLeftD);

        labelParRightD = new JLabel(")");
        labelParRightD.setFont(new Font("Dialog", Font.BOLD, 14));
        labelParRightD.setBounds(695, 249, 21, 25);
        frame.getContentPane().add(labelParRightD);

        lblInsertDestPosition = new JLabel("Insert reinforcement position:");
        lblInsertDestPosition.setBounds(315, 254, 220, 15);
        frame.getContentPane().add(lblInsertDestPosition);

    }

    public void buildAttackPhaseView(){
        labelParLeft = new JLabel("(");
        labelParLeft.setFont(new Font("Dialog", Font.BOLD, 14));
        labelParLeft.setBounds(558, 350, 21, 25);
        frame.getContentPane().add(labelParLeft);

        textFieldXposSource = new JTextField();
        textFieldXposSource.setBounds(571, 350, 41, 19);
        frame.getContentPane().add(textFieldXposSource);
        textFieldXposSource.setColumns(10);

        textFieldYposSource = new JTextField();
        textFieldYposSource.setColumns(10);
        textFieldYposSource.setBounds(648, 350, 41, 19);
        frame.getContentPane().add(textFieldYposSource);

        labelPositionDelimiterSource = new JLabel(":");
        labelPositionDelimiterSource.setBounds(630, 350, 14, 15);
        frame.getContentPane().add(labelPositionDelimiterSource);

        labelParRight = new JLabel(")");
        labelParRight.setFont(new Font("Dialog", Font.BOLD, 14));
        labelParRight.setBounds(695, 350, 21, 25);
        frame.getContentPane().add(labelParRightD);

        lblInsertSourcePosition = new JLabel("Insert source position:");
        lblInsertSourcePosition.setBounds(315, 350, 220, 15);
        frame.getContentPane().add(lblInsertSourcePosition);

        btnAddAttack = new JButton("Add Attack");
        btnAddAttack.setBounds(414, 450, 240, 25);
        frame.getContentPane().add(btnAddAttack);
    }

    public void reinforcePhaseView() {
        lblInsertUnits.setText("Reinforce with: ");

        labelParLeft.setVisible(false);
        labelParRight.setVisible(false);
        labelPositionDelimiterSource.setVisible(false);
        textFieldXposSource.setVisible(false);
        textFieldYposSource.setVisible(false);
        lblInsertSourcePosition.setVisible(false);
        btnSubmitAllReinforcements.setVisible(true);
        btnAddReinforcement.setVisible(true);
    }

    public void attackPhaseView() {
        //TODO
        lblInsertUnits.setText("Attack with");
        lblInsertSourcePosition.setText("Insert source position ");
        lblInsertDestPosition.setText("Destination position: ");
        labelParLeft.setVisible(true);
        labelParRight.setVisible(true);
        labelPositionDelimiterSource.setVisible(true);
        textFieldXposSource.setVisible(true);
        textFieldYposSource.setVisible(true);
        lblInsertSourcePosition.setVisible(true);
        btnSubmitAllReinforcements.setVisible(false);
        btnAddReinforcement.setVisible(false);
    }


    /*****************************************************************************
     * GET/SET INFO ON VIEW
     *****************************************************************************/
    /**
     * SHOW CURRENT PLAYER's SITUATION
     */
    public void showCurrentPlayersSituation(Player currentPlayer, java.util.List<Territory> territoryList){
        showLeftReinforcements(currentPlayer.getReinforcements());
        showCurrentPlayer(currentPlayer.getName());
        printPlayerTerritories(territoryList);
    }

    public void showCurrentPlayer(String name) {
        lblCurrentPlayer.setText(name);
    }

    public void printPlayerTerritories(java.util.List<Territory> list) {
        textAreaCurrentPlayerTerritories.setText("");
        for (Territory t : list) {
            textAreaCurrentPlayerTerritories.append(t.getCoordinates().toString() + "\n");
        }
    }

    public void showLeftReinforcements(int reinforcements) {
        lblReinforcementsLeft.setText("" + reinforcements);
        lblReinforcementsLeft.updateUI();
    }

    public void printArena(Arena arena) {
        model.setRowCount(0);
        int n = arena.getXsize();
        int m = arena.getYsize();
        Object[] row = {"", 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        model.addRow(row);
        for (int i = 1; i <= n; i++) {
            row = new Object[n + 1];
            row[0] = i - 1;
            for (int j = 1; j <= m; j++) {
                row[j] = arena.getTerritoryAtCoordinate(i - 1, j - 1);
            }
            model.addRow(row);
        }
    }

    public void updateTerritories(java.util.List<Territory> list){
        for (Territory t: list) {
            Point p = t.getCoordinates();
            model.setValueAt(t, (int)p.getX()+1, (int)p.getY()+1);
        }
    }

    public void updateTerritories(String playerName, int x, int y, int numOfUnits){
        model.setValueAt(playerName + "(" + numOfUnits + ")", x+1, y+1);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public void setBtnAddReinforcementListener(ActionListener a) {
        this.btnAddReinforcement.addActionListener(a);
    }

    public void setBtnAddAttackListener(ActionListener a) {
        this.btnAddAttack.addActionListener(a);
    }

    public void setBtnSubmitReinforcementsListener(ActionListener a) {
        btnAddAttack.addActionListener(a);
    }

    public void setAttackButtonListener(ActionListener a) {
        btnSubmitAllReinforcements.addActionListener(a);
    }

    public String getXposDest() {
        return textFieldXposDest.getText();
    }

    public String getYposDest() {
        return textFieldYposDest.getText();
    }

    public String getXposSource() {
        return textFieldXposSource.getText();
    }

    public String getYposSource(){
        return textFieldYposSource.getText();
    }

    public String getValue() {
        return textFieldUnits.getText();
    }
    public void showOptionPanel(String s) {
        JOptionPane.showMessageDialog(frame, s);
    }

    public void setAttackPhaseViewVisible(){
        attackPhaseView();
    }
}


