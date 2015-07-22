package tora.train.risk.clientserver.common;

/**
 * Interface for all controllers
 *
 * Created by Andrea on 7/16/2015.
 */
public interface Controller {

    /**
     * Modificatorul 'public' este redundant la metodele interfetelor. Teoretic nu are sens ca o interfata sa
     * contina metode non publice. Practic este restrictionat de compilator.
     */

    /**
     * Prezenta metodei 'stopRunning' (care putea fi mai simplu numita 'stop') indica nevoia (sau cere) prezenta
     * unei metode 'start'. Aceasta simetrie start/stop, setUp/tearDown, registerListener/unregisterListener este
     * importanta pentru a garanta faptul ca diverse componente din sistem sunt initializate cand trebuie si oprite
     * cand nu mai este nevoie de ele. Mai mult, simetria, o data construita, este foarte usor de intretinut:
     * EG: in interiorul metodei start() se apeleaza registerListener(), iar in interiorul metodei stop() se apeleaza
     * unregisterListener().
     */
    void stopRunning();

    /**
     * Din nou, simetria cere nume de metode compatibile: read/write sau send/receive.
     */
    void sendMessage(Message msg);
    Message readMessage();
}
