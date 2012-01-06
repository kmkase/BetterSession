/**
 * Author: Omar García
 * Date: 18/10/11
 * Time: 03:14 PM
 */
package play.modules.bettersession;

import play.Logger;
import play.modules.bettersession.utilities.CookieSessionUtility;

/**
 * Class that control the usage of the cookie session: Its used to create, remove,
 * also too check if exists the session cookie and send the value stored in it.
 * Besides allow manages the remember cookie too.
 */
public abstract class CookieSessionEvents {

    static void createSessionIntoCookie(String key, String value, boolean remember) {
        // -----
        // Methods to crypt value
        // New methods to make session and remember cookie
        CookieSessionUtility.putSession(key, value);
        if (remember) {
            CookieSessionUtility.makeCookie(BetterSessionUtility.REMEMBER, value, "30d");
//            Logger.debug("%n~~~~~ Remember COOKIE Created ~~~~~");
        }

//        Logger.debug("%n~~~~~ Created into COOKIE key: [%s], value: [%s] ~~~~~", key, value);
    }

    static void removeSessionFromCookie(String key) {
        // -----
        // Methods to crypt value
        CookieSessionUtility.removeSession();
        CookieSessionUtility.removeCookie(BetterSessionUtility.REMEMBER);
//        Logger.debug("%n~~~~~ Dropped from COOKIE key: [%s] ~~~~~", key);
    }

    /**
     * Get the session that was store into the remember cookie
     * @return String - Unique value used to create the session that was store if it exists
     */
    static String getRememberCookieValue() {
        // -----
        // Methods to crypt value
        return CookieSessionUtility.getCookie(BetterSessionUtility.REMEMBER);
    }

    /**
     * This method check if a session cookie exists
     *
     * @param key - Used to store into cookie session the value
     * @return true - if a session cookie exists with that KEY
     */
    static boolean isSessionIntoCookie(String key) {
        // -----
        // Methods to crypt value
//        return containsSession(key);
        return (CookieSessionUtility.containsSession(key) && getSessionValueFromCookie(key) != null);
    }

    /**
     * This method return the value stored into cookie session
     *
     * @param key - Key of the cookie to get its value
     * @return String - the value that contains the Cookie session with that KEY
     */
    static String getSessionValueFromCookie(String key) {
        return CookieSessionUtility.getSessionValue(key);
    }

    /**
     * This method return the Date when the session was make
     *
     * @param key - Key of the cookie to get its date
     * @return long - Date in mili-seconds
     */
    public static long getSessionDateFromCookie(String key) {
        return CookieSessionUtility.getDateOfSession(key);
    }

}
