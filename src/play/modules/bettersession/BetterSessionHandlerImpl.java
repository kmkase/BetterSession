/**
 * Author: Omar García
 * Date: 6/10/11
 * Time: 04:01 PM
 */
package play.modules.bettersession;

import play.Logger;
import play.modules.bettersession.handlers.BetterSessionHandler;

public class BetterSessionHandlerImpl implements BetterSessionHandler {

    /**
     * Create the session into the cookie and the cache session
     * This can be used if only just want the session cookie
     *
     * @param value - Unique value used to create the session
     * @param remember - If is needed to create a remember cookie this must be true
     */
    public void create(String value, Boolean remember) {
        if (BetterSessionUtility.IS_ENABLE) {
//            String key = makeKeyOfSessionCache(value);
//            Logger.debug("key: %s, value: %s, ttl: %s", key, value, BetterSessionUtility.TIME_TO_LIVE);
            CacheSessionEvents.storeSessionIntoCache(makeKeyOfSessionCache(value), value, BetterSessionUtility.TIME_TO_LIVE);
            CookieSessionEvents.createSessionIntoCookie(BetterSessionUtility.COOKIE_SESSION_KEY, value, remember);
//            Logger.debug("~~~~~ Created uniqueSession ~~~~~");
        }
        CookieSessionEvents.createSessionIntoCookie(BetterSessionUtility.COOKIE_SESSION_KEY, value, remember);
    }

    /**
     * Drop the session that exists into the cache and cookie
     * This can be used if only just want the session cookie
     */
    public void remove() {
        if (BetterSessionUtility.IS_ENABLE) {
            String value = CookieSessionEvents.getSessionValueFromCookie(BetterSessionUtility.COOKIE_SESSION_KEY);
            CacheSessionEvents.deleteSessionFromCache(makeKeyOfSessionCache(value));
            CookieSessionEvents.removeSessionFromCookie(BetterSessionUtility.COOKIE_SESSION_KEY);
//            Logger.debug("~~~~~ Dropped uniqueSession ~~~~~");
        }
        CookieSessionEvents.removeSessionFromCookie(BetterSessionUtility.COOKIE_SESSION_KEY);
    }

    /**
     * Method used to verify if the session is created into cookie or cache
     *
     * @param value - Unique value used to create the session
     * @return true - If the session its contained into the cookie or cache session
     */
    public boolean exist(String value) {
        // -----
        // Condition that check if the request contains a session Cookie or if the session is into Cache
        if (BetterSessionUtility.IS_ENABLE) {
            if (CacheSessionEvents.isSessionIntoCache(makeKeyOfSessionCache(value)) || (CookieSessionEvents.getSessionValueFromCookie(BetterSessionUtility.COOKIE_SESSION_KEY) != null && CookieSessionEvents.getSessionValueFromCookie(BetterSessionUtility.COOKIE_SESSION_KEY).equals(value))) {
                Logger.debug("===== Session already exists =====");
                return true;
            }
        }
        return false;
//        return isSessionIntoCache(value) || (getSessionValueFromCookie() != null && getSessionValueFromCookie().equals(value));
    }

    /**
     * This method its used to change the session into the cache using the cookie session
     * Its used to allow the consistency into the cookie and the cache session
     */
    public void renew() {
        if (BetterSessionUtility.IS_ENABLE) {
            if (isConnected()) {
                String value = getSessionValue();
                CacheSessionEvents.renewSessionIntoCache(makeKeyOfSessionCache(value), value, BetterSessionUtility.TIME_TO_LIVE);
//                Logger.debug("~~~~~ Renewed uniqueSession ~~~~~");
            }
        }
    }

    /**
     * Check if the session is stored into a remember cookie
     *
     * @return true - If the session its stored into remember cookie
     */
    public boolean isRememberCookieCreated() {
        // -----
        // Get from remember cookie
        String value = CookieSessionEvents.getRememberCookieValue();
        if (value != null) {
            if(BetterSessionUtility.IS_ENABLE) {
                return !CacheSessionEvents.isSessionIntoCache(makeKeyOfSessionCache(value));
            }
            return true;
        }
        return false;
    }

    /**
     * If a remember cookie renew the session into Cookie and Cache
     */
    public void renewSessionFromRememberCookie() {
        // -----
        // Get from remember cookie
        String value;
        if(isRememberCookieCreated()) {
            value = CookieSessionEvents.getRememberCookieValue();
//            if(BetterSessionUtility.IS_ENABLE) {
//                // Only will create the session if another same one doesn't exist
//                if(!CacheSessionEvents.isSessionIntoCache(makeKeyOfSessionCache(value))) {
//                    create(value, false);
//                } else {
//                    // otherwise remove the session
//
//                }
//            } else {
//                create(value, false);
//            }
            create(value, false);
        }
    }

    /**
     * This method return if session is into cookie and cache
     *
     * @return true - if a session cookie and cache exists
     */
    public boolean isConnected() {
        // -----
        // Check if cookie session exists
        boolean flag = false;

        // Check if the value is contain it into cache and cookie
        if (getSessionValue() != null) {
            if (BetterSessionUtility.IS_ENABLE) {
                if (CacheSessionEvents.isSessionIntoCache(makeKeyOfSessionCache(getSessionValue()))) {
                    flag = true;
                }
            } else {
                flag = true;
            }
        }

//        Logger.debug("~~~~~ Session is Connected: [%s] ~~~~~", flag);
        return flag;
    }

    /**
     * This method return the value used to create and store the session into coolie and cache
     *
     * @return String - value used to create the session into cookie and cache
     */
    public String getSessionValue() {
        String value = null;
        if (CookieSessionEvents.isSessionIntoCookie(BetterSessionUtility.COOKIE_SESSION_KEY)) {
            value = CookieSessionEvents.getSessionValueFromCookie(BetterSessionUtility.COOKIE_SESSION_KEY);

            if (BetterSessionUtility.IS_ENABLE) {
                // Check if the value is contain it into cache
                if (CacheSessionEvents.isSessionIntoCache(makeKeyOfSessionCache(value))) {
                    return value;
                } else {
                    // Leaves the session consistent if it requires
//                    Logger.debug("~~~~~ Session deleted from Cookie, because didn't exist into Cache ~~~~~");
                    CookieSessionEvents.removeSessionFromCookie(BetterSessionUtility.COOKIE_SESSION_KEY);
                    value = null;
                }
            }
        }
        return value;
    }

    /**
     * Return the date when was created the session
     * @return Date
     */
    public long getDateFromSession() {
        long date = 0;
        if (CookieSessionEvents.isSessionIntoCookie(BetterSessionUtility.COOKIE_SESSION_KEY)) {
            String value = CookieSessionEvents.getSessionValueFromCookie(BetterSessionUtility.COOKIE_SESSION_KEY);
            date = CookieSessionEvents.getSessionDateFromCookie(BetterSessionUtility.COOKIE_SESSION_KEY);

            if (BetterSessionUtility.IS_ENABLE) {
                // Check if the date is contain it into cache
                if (CacheSessionEvents.isSessionIntoCache(makeKeyOfSessionCache(value))) {
                    return date;
                } else {
                    // Leaves the session consistent if it requires
//                    Logger.debug("~~~~~ Session deleted from Cookie, because didn't exist into Cache ~~~~~");
                    CookieSessionEvents.removeSessionFromCookie(BetterSessionUtility.COOKIE_SESSION_KEY);
//                    return date;
                }
            }
        }
        return date;
    }

    // ==============================================
    // =============== START UTILITY METHODS ===============
    // ==============================================

    /**
     * Make a cache session key using the value of the unique field sent
     *
     * @param value - This value must be unique to ensure that the key won't be repeated
     * @return String - key that will be used to storage into cache the session
     */
    private String makeKeyOfSessionCache(String value) {
        return String.format("%s-%s", BetterSessionUtility.CACHE_SESSION_KEY, value);
    }

}
