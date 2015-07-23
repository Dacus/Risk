package tora.train.risk.GUI;

import tora.train.risk.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by intern on 7/20/15.
 */
public class LoginController  {
    LoginView frame;

    public LoginController() {
        frame = new LoginView();

        frame.setBtnAddPlayersListener(new AddPlayerAction());
        frame.setBtnSubmitListener(new SubmitAction());
    }

    private class AddPlayerAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean validName=true;
            for (JTextField tf :frame.getListOfTextFields()) {
                String nume = tf.getText();
                validName&= !nume.equals("");
                if (validName) {
                    StaticInformations.addPlayer(new Player(nume));
                } else {
                    frame.getStatusBar().setText("All fields are mandatory!!!");
                    for (JTextField t : frame.getListOfTextFields()) {
                        t.setVisible(false);
                    }
                    frame.getListOfTextFields().clear();
                    frame.firstView();
                    break;
                }
            }
            if (validName){
                frame.setStatusBar("players added");
                MainController mainWindow=new MainController();
                mainWindow.setVisible(true);
                frame.setVisible(false);
            }
        }
    }

    private class SubmitAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.newListOfTextFields();
            int playersNr = 0;
            try {
                playersNr = Integer.parseInt(frame.getTextFieldPlayersNr().getText());
                if (playersNr > 8) {
                    frame.getStatusBar().setText("Maximum number of players is 8!");
                } else {
                    frame.getStatusBar().setText(playersNr + " players");
                    frame.secondView();
                    int height = 150;
                    for (int i = 0; i < playersNr; i++) {
                        JTextField tf = new JTextField();
                        tf.setBounds(50, height, 153, 15);
                        frame.addComponent(tf);
                        tf.setColumns(10);
                        frame.addTextFieldInList(tf);
                        height += 30;
                    }
                }

            } catch (NumberFormatException e1) {
                frame.getStatusBar().setText("Please insert a number > 1");
                e1.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginController window = new LoginController();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
