/**
 * Author: Omar García
 * Date: 18/10/11
 * Time: 10:24 AM
 */
package play.modules.bettersession;

import play.modules.bettersession.handlers.NotifySessionAidHandler;
import play.modules.bettersession.model.SessionAid;

import java.util.List;

public abstract class SessionAidEvents {

    private static NotifySessionAidHandler notifySessionAid;

    static void notifyElementStored(String key, String value) {
        notifySessionAid.notifyElementStored(key, value);
    }

    static void notifyElementRemoved(String key) {
        notifySessionAid.notifyElementRemoved(key);
    }

    static void notifyElementUpdated(String key, String value) {
        notifySessionAid.notifyElementUpdated(key, value);
    }

    public static void notifyClearElements() {
        notifySessionAid.notifyClearElements();
    }

    static void notifyElementExpired() {
        notifySessionAid.notifyElementExpired();
    }

    public static List<SessionAid> getAllElements() {
        return notifySessionAid.getAllElements();
    }

    static boolean existElement(String key) {
        return notifySessionAid.existElement(key);
    }

    /**
     * Initialize the instance of NotifySessionAidHandler
     */
    static void init() {
        notifySessionAid = new NotifySessionAidImpl();
    }

}
