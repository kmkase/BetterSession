/**
 * Author: Omar García
 * Date: 10/10/11
 * Time: 03:25 PM
 */
package play.modules.bettersession;

import play.modules.bettersession.handlers.BetterSessionEventHandler;

public abstract class BetterSession {

    // TODO: Add method to add owns implementations of BetterSessionEventHandler

    private static BetterSessionEventHandler sessionEventHandler;

    public static void createSession(String value, Boolean remember) {
        sessionEventHandler.create(value, remember);
    }

    public static void dropSession() {
        sessionEventHandler.remove();
    }

    public static boolean existSession(String value) {
        return sessionEventHandler.exist(value);
    }

    public static void renewSession() {
        sessionEventHandler.renew();
    }

    public static boolean isRememberCookieCreated() {
        return sessionEventHandler.isRememberSessionCookieCreated();
    }

    public static boolean isConnected() {
        return sessionEventHandler.isConnected();
    }

    public static String connected() {
        return sessionEventHandler.getSessionValue();
    }

    public static long connectedAt() {
        return sessionEventHandler.getDateFromSession();
    }

    public static void init() {
//        if (customEventHandler != null) {
//            sessionEventHandler = customEventHandler;
//            return;
//        }
//        sessionEventHandler = BetterSessionEventImpl.getInstance();
        sessionEventHandler = new BetterSessionEventImpl();
    }

}
