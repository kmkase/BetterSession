/**
 * Author: Omar García
 * Date: 10/10/11
 * Time: 03:25 PM
 */
package play.modules.bettersession;

import play.modules.bettersession.handlers.BetterSessionHandler;

public abstract class BetterSession {

    // TODO: Add method to add owns implementations of BetterSessionHandler

    private static BetterSessionHandler betterSessionHandler;

    public static void createSession(String value, Boolean remember) {
        betterSessionHandler.create(value, remember);
    }

    public static void dropSession() {
        betterSessionHandler.remove();
    }

    public static boolean existSession(String value) {
        return betterSessionHandler.exist(value);
    }

    public static void renewSession() {
        betterSessionHandler.renew();
    }

    public static boolean isRememberCookieCreated() {
        return betterSessionHandler.isRememberCookieCreated();
    }

    public static void renewSessionFromRememberCookie() {
        betterSessionHandler.renewSessionFromRememberCookie();
    }

    public static boolean isConnected() {
        return betterSessionHandler.isConnected();
    }

    public static String getSessionValue() {
        return betterSessionHandler.getSessionValue();
    }

    public static long connectedAt() {
        return betterSessionHandler.getDateFromSession();
    }

    public static void init() {
//        if (customEventHandler != null) {
//            betterSessionHandler = customEventHandler;
//            return;
//        }
//        betterSessionHandler = BetterSessionHandlerImpl.getInstance();
        betterSessionHandler = new BetterSessionHandlerImpl();
    }

}
