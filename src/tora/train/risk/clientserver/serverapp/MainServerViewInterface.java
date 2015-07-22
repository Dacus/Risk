package tora.train.risk.clientserver.serverapp;

/**
 * Created by Andrea on 7/16/2015.
 */
/**
 *  Interfetele sunt folosite pentru a declara metode, nu pentru a encapsula constante.
 *  Pentru a determina daca e sau nu nevoie de o interfata, imagineaza-ti scenariul urmator:
 *
 *      MainServerViewInterface mainServerView = new MainServerViewImplementation();
 *
 *  Am cum sa folosesc mainServerView? Nu, pentru ca interfata nu contine nici-o metoda.
 */
public interface MainServerViewInterface {
    int WINDOW_X=500;
    int WINDOW_Y=500;
}
