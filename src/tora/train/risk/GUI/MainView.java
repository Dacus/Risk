package tora.train.risk.GUI;

import tora.train.risk.Player;
import tora.train.risk.Territory;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView {
	//TODO

	private JFrame frame;
	private JLabel lblCurrentPlayer;
	private JTable tableArena;
	private DefaultTableModel model;
	private JLabel lblYourTerritories;
    private JTextArea textArea;
    private JTextField textFieldXpos;
    private JTextField textFieldYpos;
    private JLabel labelParLeft;
    private JLabel labelParRight;
    private JLabel lblReinforcements;
    private JTextField textFieldReinforcements;
    private JButton btnAddReinforcement;
    private JButton btnSubmitAllReinforcements;
    private JLabel lblReinforcementsLeft;



	/**
	 * Create the application.
	 */
	public MainView() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 592);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblPlayer = new JLabel("Player: ");
		lblPlayer.setBounds(43, 17, 122, 25);
		frame.getContentPane().add(lblPlayer);
		
		lblCurrentPlayer = new JLabel("");
		lblCurrentPlayer.setBounds(600, 29, 150, 15);
		frame.getContentPane().add(lblCurrentPlayer);

        lblReinforcementsLeft = new JLabel("~");
        lblReinforcementsLeft.setBounds(353, 22, 150, 15);
        frame.getContentPane().add(lblReinforcementsLeft);

		Object[] data = {" ",0,1,2,3,4,5,6,7,8,9,10};
		model =new DefaultTableModel(0,1);
        for (int i=0;i<11;i++){
            model.addColumn(i,data);
        }

		tableArena = new JTable(model);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tableArena.setDefaultRenderer(Object.class, centerRenderer);
		tableArena.setBounds(12, 44, 876, 198);
		frame.getContentPane().add(tableArena);
		
		lblYourTerritories = new JLabel("Your territories are at positions:");
		lblYourTerritories.setBounds(12, 254, 242, 15);
		frame.getContentPane().add(lblYourTerritories);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(22, 275, 232, 265);
		frame.getContentPane().add(textArea);
		
		textFieldXpos = new JTextField();
		textFieldXpos.setBounds(571, 254, 41, 19);
		frame.getContentPane().add(textFieldXpos);
		textFieldXpos.setColumns(10);
		
		textFieldYpos = new JTextField();
		textFieldYpos.setColumns(10);
		textFieldYpos.setBounds(648, 254, 41, 19);
		frame.getContentPane().add(textFieldYpos);
		
		JLabel labelPosDelimiter = new JLabel(":");
		labelPosDelimiter.setBounds(630, 256, 14, 15);
		frame.getContentPane().add(labelPosDelimiter);
		
		JLabel lblInsertPosition = new JLabel("Insert reinforcement position:");
		lblInsertPosition.setBounds(315, 254, 220, 15);
		frame.getContentPane().add(lblInsertPosition);

		labelParLeft = new JLabel("(");
		labelParLeft.setFont(new Font("Dialog", Font.BOLD, 14));
		labelParLeft.setBounds(558, 249, 21, 25);
		frame.getContentPane().add(labelParLeft);
		
		labelParRight = new JLabel(")");
		labelParRight.setFont(new Font("Dialog", Font.BOLD, 14));
		labelParRight.setBounds(695, 249, 21, 25);
		frame.getContentPane().add(labelParRight);
		
		lblReinforcements = new JLabel("Reinforcements: ");
		lblReinforcements.setBounds(315, 306, 151, 15);
		frame.getContentPane().add(lblReinforcements);
		
		textFieldReinforcements = new JTextField();
		textFieldReinforcements.setBounds(575, 304, 114, 19);
		frame.getContentPane().add(textFieldReinforcements);
		textFieldReinforcements.setColumns(10);
		
		btnAddReinforcement = new JButton("Add Reinforcement");
        btnAddReinforcement.setBounds(414, 450, 240, 25);
		frame.getContentPane().add(btnAddReinforcement);
		
		btnSubmitAllReinforcements = new JButton("Submit All Reinforcements");
        btnSubmitAllReinforcements.setBounds(414, 400, 240, 25);
		frame.getContentPane().add(btnSubmitAllReinforcements);
	}

    public void reinforcePhaseView(){
        //TODO
        btnAddReinforcement.setVisible(true);
        btnSubmitAllReinforcements.setVisible(true);
        showLeftReinforcements();
        showCurrentPlayer();
        printPlayersTerritories(StaticInformations.getCurrentPlayer());
    }

    public void attackPhaseView(){
        //TODO
    }


    public void printPlayersTerritories(Player p){
        textArea.setText("");
        for (Territory t:StaticInformations.getPlayersTerritories(p)){
            textArea.append(t.getCoordinates().toString()+"\n");
        }
    }

    public void printMap(){
        model.setRowCount(0);
        int n=StaticInformations.getXsize(),
                m=StaticInformations.getYsize();
        Object [] row={"",0,1,2,3,4,5,6,7,8,9,10};
        model.addRow(row);
        for (int i=1;i<=n;i++){
            row=new Object[n+1];
            row[0]=i-1;
            for (int j=1;j<=m; j++){
                row[j]=StaticInformations.getTerritoryAtCoordinate(i-1,j-1);
            }
            model.addRow(row);
        }
    }

    public void showCurrentPlayer() {
        lblCurrentPlayer.setText(StaticInformations.getCurrentPlayer().getName());
    }

    public void showLeftReinforcements(){
        lblReinforcementsLeft.setText(""+StaticInformations.getCurrentPlayer().getReinforcements());
        lblReinforcementsLeft.updateUI();
    }

    public void setVisible(boolean visible){
        frame.setVisible(visible);
    }

    public void setBtnAddReinforcementListener(ActionListener a){
        this.btnAddReinforcement.addActionListener(a);
    }

    public void setBtnSubmitReinforcementsListener(ActionListener a){
        btnSubmitAllReinforcements.addActionListener(a);
    }

    public int getXpos() {
        int x = -1;
        try {
            x = Integer.parseInt(textFieldXpos.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please insert numerical values");
            e.printStackTrace();
        }
        return x;
    }

    public int getYpos() {
        int x = -1;
        try {
            x = Integer.parseInt(textFieldYpos.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please insert numerical values");
            e.printStackTrace();
        }
        return x;
    }

    public int getValue() {
        int x = -1;
        try {
            x = Integer.parseInt(textFieldReinforcements.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please insert numerical values");
            e.printStackTrace();
        }
        return x;
    }
}
