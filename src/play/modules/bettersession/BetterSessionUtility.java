/**
 * Author: Omar García
 * Date: 16/11/11
 * Time: 11:07 AM
 */
package play.modules.bettersession;

import play.Play;

public class BetterSessionUtility {

    private static final String IMPROVED_SESSION_CONFIG_PREFIX = "bettersession";

    public static final String IMPROVED_SESSION_CONFIG_ENABLE = String.format( "%s.enable", BetterSessionUtility.IMPROVED_SESSION_CONFIG_PREFIX);

    public static final String IMPROVED_SESSION_CONFIG_KEY_PREFIX = String.format( "%s.keyPrefix", BetterSessionUtility.IMPROVED_SESSION_CONFIG_PREFIX);

    public static final String IMPROVED_SESSION_CONFIG_TIME_TO_LIVE = String.format( "%s.timeToLive", BetterSessionUtility.IMPROVED_SESSION_CONFIG_PREFIX);

    public static final String PLAY_SESSION_MAX_AGE = "application.session.maxAge";

    public static final String PLAY_SESSION_COOKIE_PREFIX = "application.session.cookie";

    public static final String COOKIE_SESSION_KEY = "better_session";

    public static String REMEMBER = "remember";

    public static String CACHE_SESSION_KEY;

    public static final boolean IS_ENABLE = isEnable();

    public static String TIME_TO_LIVE;


    public static boolean isEnable() {
        return Boolean.parseBoolean(Play.configuration.getProperty(BetterSessionUtility.IMPROVED_SESSION_CONFIG_ENABLE));
    }

    public static String getTimeToLive() {
        return Play.configuration.getProperty(BetterSessionUtility.IMPROVED_SESSION_CONFIG_TIME_TO_LIVE);
    }

    public static String getPrefix() {
        return Play.configuration.getProperty(BetterSessionUtility.IMPROVED_SESSION_CONFIG_KEY_PREFIX);
    }

}
