package tora.train.risk.GUI;

import tora.train.risk.Player;

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
            /**
             * Care name? Ce zici de:
             *      if (frame.validateNames()) {
             *          ...
             *      } else {
             *          frame.setStatus("Player names cannot be empty");
             *      }
             */
            boolean validName=true;
            for (JTextField tf :frame.getListOfTextFields()) {
                String nume = tf.getText();
                validName&= !nume.equals("");
                if (validName) {
                    /**
                     * Daca ai nevoie de o lista de player ca sa initializezi un joc, de ce nu construiesti un context
                     * pe care sa-l transmiti la constructor? Habar nu am cine foloseste informatiile din StaticInformation.
                     *
                     * Daca o componenta (LoginController) scrie in StaticInformation si o alta componenta (???) citeste,
                     * aceste componente devin legate logic si trebuiesc sincronizate constant. Intrebari de genul:
                     *      - De ce nu sunt toti playerii in lista?
                     *      - Cine a sters player-ul ala?
                     *      - Unde sa modific lista ca alta componenta sa nu se dea peste cap?
                     * iti vor scoate peri albi si nu vei mai putea imbunatati arhitectura.
                     *
                     * Considera obiecte mici (contexte) pe care sa le construiesti in componenta A si sa le transmiti la
                     * componenta B atunci cand e nevoie.
                     */
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
                /**
                 * Aici se afiseaza un status pe frame, apoi se ascunde frame-ul.
                 */
                frame.setStatusBar("players added");

                /**
                 * mainWindow e un controller. M-as fi asteptat sa se numeasca mainController. Daca tot e controller,
                 * ar putea fi construit de la bun inceput, iar apoi folosit pentru a afisa fereastra.
                 */
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

                        /**
                         * Nu inteleg de ce controller-ul trebuie sa construiasca obiecte swing. Ar fi fost mai potrivit
                         * prezenta unei metode in clasa LoginView care sa adauge aceste componente. Si nu ma refer la
                         * 'addComponent'. Ma refer la o metoda care sa poarte un nume legat de latura de 'business' a
                         * codului. Ceva similar cu
                         *
                         *      frame.addUserField(...)
                         *
                         * care sa comunice rolul componentei care va fi adaugate, si care sa encapsuleze toata logica
                         * de UI.
                         */

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
