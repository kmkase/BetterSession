/**
 * Author: Omar García
 * Date: 18/10/11
 * Time: 03:14 PM
 */
package play.modules.bettersession;

import play.Logger;
import play.cache.Cache;
import play.modules.bettersession.model.SessionAid;

import java.util.List;

/**
 * Class to control the usage of the Cache session
 */
public abstract class CacheSessionEvents {

    /**
     * Store into cache the session with the params sent this must be unique
     *
     * @param key - Used to create the session from cache
     * @param value - The value stored with the key
     * @param ttl - TimeToLive of this session
     */
    static void storeSessionIntoCache(String key, String value, String ttl) {
        // -----
        Cache.safeAdd(key, value, ttl);
//        Logger.debug("%n~~~~~ Stored into CACHE key: [%s], value: [%s], TTL: [%s] ~~~~~", key, value, ttl);
        if (!SessionNotifies.existElement(key)) {
            SessionNotifies.notifyElementStored(key, value);
        }
    }

    /**
     * Delete from cache the session that it's using the unique value sent
     *
     * @param key - Used to find and delete that session from cache
     */
    public static void deleteSessionFromCache(String key) {
        // -----
        Cache.safeDelete(key);
//        Logger.debug("%n~~~~~ Deleted from CACHE key: [%s] ~~~~~", key);
        SessionNotifies.notifyElementRemoved(key);
    }

    /**
     * Replace the session that was create into the cache
     *
     * @param key - Used to find and replace that session from cache
     * @param value - Unique value used to find and replace that session into cache
     * @param ttl - TImeToLive used to contain alive the session into cache
     */
    static void renewSessionIntoCache(String key, String value, String ttl) {
        // -----
        Cache.safeReplace(key, value, ttl);
//        Logger.debug("%n~~~~~ Renewed into CACHE key: [%s], value: [%s], TTL: [%s] ~~~~~", key, value, ttl);
//        SessionNotifies.notifyElementUpdated(key, value);    // This line only works to Test purpose
    }

    /**
     * This method check if a certain value is contained into cache and return a boolean value
     *
     * @param key - Used to find session from cache
     * @return true - if the value is contained into cache
     */
    static boolean isSessionIntoCache(String key) {
        // -----
        // Check that the value into cache and the Key sent are equals
        if (Cache.get(key) != null) {
            // This condition make slow the request
//            if (!SessionNotifies.existElement(key)) {
//                SessionNotifies.notifyElementStored(key, (String) Cache.get(key));
//            }
//            Logger.debug("%n~~~~~ Session CACHE is OK ~~~~~");
            return true;
        }
        return false;
    }

    /**
     * Restore the sessions stored into the Model if was lost from Cache
     * NOTE: This method only must be invoked when applicationStart
     */
    static void restoreCacheFromModel() {
        // -----
        // If into the MODEL layer have sessions are restored into the CACHE
        if (BetterSessionUtility.IS_ENABLE) {
            List<SessionAid> sessionAidList = SessionNotifies.getAllElements();
            if (!sessionAidList.isEmpty()) {
                for (SessionAid sessionAid : sessionAidList) {
//                    Logger.debug("%n~~~~~ Session MODEL contains key: [%s], value: [%s] ~~~~~", sessionAid.getKey_name(), sessionAid.getValue_name());
                    // This reload the Cache with the info stored into Model
                    storeSessionIntoCache(sessionAid.getKey_name(), sessionAid.getValue_name(), BetterSessionUtility.TIME_TO_LIVE);
                }
                Logger.debug("===== CACHE session reloaded =====");
            }
        }
    }

}
