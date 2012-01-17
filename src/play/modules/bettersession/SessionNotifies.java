/**
 * Author: Omar García
 * Date: 18/10/11
 * Time: 10:24 AM
 */
package play.modules.bettersession;

import play.modules.bettersession.handlers.SessionNotifiable;
import play.modules.bettersession.model.SessionAid;

import java.util.List;

public abstract class SessionNotifies {

    private static SessionNotifiable sessionNotifiable;

    static void notifyElementStored(String key, String value) {
        sessionNotifiable.notifyElementStored(key, value);
    }

    static void notifyElementRemoved(String key) {
        sessionNotifiable.notifyElementRemoved(key);
    }

    static void notifyElementUpdated(String key, String value) {
        sessionNotifiable.notifyElementUpdated(key, value);
    }

    public static void notifyClearElements() {
        sessionNotifiable.notifyClearElements();
    }

    static void notifyElementExpired() {
        sessionNotifiable.notifyElementExpired();
    }

    public static List<SessionAid> getAllElements() {
        return sessionNotifiable.getAllElements();
    }

    static boolean existElement(String key) {
        return sessionNotifiable.existElement(key);
    }

    /**
     * Initialize the instance of SessionNotifiable
     */
    static void init() {
        sessionNotifiable = new SessionNotifiableImpl();
    }

}
