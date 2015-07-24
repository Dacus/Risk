package tora.train.risk.GUI;

import tora.train.risk.Arena;
import tora.train.risk.Player;
import tora.train.risk.Territory;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView {

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
    private JButton btnSubmitAllAttacks;


    /**
     * Create the application.
     */
    public MainView() {
        initialize();
        buildReinforcePhaseView();
        buildAttackPhaseView();
        reinforcePhaseView();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
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
        model = new DefaultTableModel();
        for (int i = 0; i < 11; i++) {
            model.addColumn(data);
        }

        tableArena = new JTable(model);
        tableArena.setEnabled(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableArena.setDefaultRenderer(Object.class, centerRenderer);

        JScrollPane scrollPane=new JScrollPane(tableArena, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(12, 44, 876, 198);
        frame.getContentPane().add(scrollPane);

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

        textFieldXposDest = new JTextField(10);
        textFieldXposDest.setBounds(571, 254, 41, 19);
        frame.getContentPane().add(textFieldXposDest);

        textFieldYposDest = new JTextField(10);
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

        btnSubmitAllAttacks = new JButton("Submit All Reinforcements");
        btnSubmitAllAttacks.setBounds(414, 400, 240, 25);
        frame.getContentPane().add(btnSubmitAllAttacks);
    }

    public void reinforcePhaseView() {
        //TODO
        lblInsertUnits.setText("Reinforce with: ");

        labelParLeft.setVisible(false);
        labelParRight.setVisible(false);
        labelPositionDelimiterSource.setVisible(false);
        textFieldXposSource.setVisible(false);
        textFieldYposSource.setVisible(false);
        lblInsertSourcePosition.setVisible(false);
        btnSubmitAllReinforcements.setVisible(true);
        btnAddReinforcement.setVisible(true);

        showLeftReinforcements();
        showCurrentPlayer();
        printPlayersTerritories(StaticInformations.getCurrentPlayer());
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


    public void printPlayersTerritories(Player p) {
        textAreaCurrentPlayerTerritories.setText("");
        for (Territory t : StaticInformations.getPlayersTerritories(p)) {
            textAreaCurrentPlayerTerritories.append(t.getCoordinates().toString() + "\n");
        }
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

    public void showCurrentPlayer() {
        lblCurrentPlayer.setText(StaticInformations.getCurrentPlayer().getName());
    }

    public void showLeftReinforcements() {
        lblReinforcementsLeft.setText("" + StaticInformations.getCurrentPlayer().getReinforcements());
        lblReinforcementsLeft.updateUI();
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
        btnSubmitAllReinforcements.addActionListener(a);
    }

    public void setBtnSubmitAllAttacksListener(ActionListener a) {
        btnSubmitAllAttacks.addActionListener(a);
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

    public String getYposSource() {
       return textFieldYposSource.getText();
    }

    public String getValue() {
       return textFieldUnits.getText();
    }
}


