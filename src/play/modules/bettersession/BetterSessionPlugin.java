/**
 * Author: Omar García
 * Date: 5/10/11
 * Time: 03:31 PM
 */
package play.modules.bettersession;

import play.Logger;
import play.Play;
import play.PlayPlugin;
import play.exceptions.ConfigurationException;
import play.modules.bettersession.utilities.JdbcUtility;
import play.mvc.Router;

public class BetterSessionPlugin extends PlayPlugin {

    @Override
    public void onLoad() {
        // -----
        // Loading configuration by default
        String sessionMaxAge = Play.configuration.getProperty(BetterSessionUtility.PLAY_SESSION_MAX_AGE);
        Play.configuration.setProperty(BetterSessionUtility.IMPROVED_SESSION_CONFIG_TIME_TO_LIVE, sessionMaxAge != null ? sessionMaxAge : "10mn" );
        if (sessionMaxAge == null && BetterSessionUtility.isEnable()) {
            String error = String.format("=== BetterSession is enable and require to work you define the property [%s] to work well ===", BetterSessionUtility.PLAY_SESSION_MAX_AGE);
            String msg = String.format("=== It is advisable use a [%s] SHORT for the session for a better  ===", BetterSessionUtility.PLAY_SESSION_MAX_AGE);
            Logger.fatal(error);
            Logger.fatal(msg);
            throw new ConfigurationException(error);
        }

        String sessionCookiePrefix = Play.configuration.getProperty(BetterSessionUtility.PLAY_SESSION_COOKIE_PREFIX);
        Play.configuration.setProperty(BetterSessionUtility.IMPROVED_SESSION_CONFIG_KEY_PREFIX, sessionCookiePrefix != null ? sessionCookiePrefix : "PLAY" );

        BetterSessionUtility.CACHE_SESSION_KEY = String.format("%s_%s", BetterSessionUtility.getPrefix(), BetterSessionUtility.COOKIE_SESSION_KEY);
        BetterSessionUtility.TIME_TO_LIVE = BetterSessionUtility.getTimeToLive();

        // Start the JdbcUtility and create the table
        JdbcUtility.init();

        // Make instances of the implementations...
        BetterSession.init();
        SessionAidEvents.init();

    }

    @Override
    public void onApplicationStart() {
        // -----
        // If the table is already exist and is not empty then reload the Cache
        CacheSessionEvents.restoreCacheFromModel();

        // Add to routes the URL to clear session. NOTE: Only works in dev mode
        if (Play.mode.isDev()) {
            Router.addRoute("GET", "/@clear-session/?", "bettersession.BetterSessionController.clearSession");
        }
    }

    @Override
    public void onInvocationSuccess() {
        // -----
        // To leave stable the Cache and Model sessions
        SessionAidEvents.notifyElementExpired();

        // Verify if is needed do a renew of session
        BetterSession.renewSession();
    }

}
