/**
 * Author: omar2
 * Date: 1/12/11
 * Time: 11:16 AM
 */
package controllers.bettersession;

import play.Logger;
import play.cache.Cache;
import play.modules.bettersession.BetterSessionUtility;
import play.modules.bettersession.CacheSessionEvents;
import play.modules.bettersession.SessionAidEvents;
import play.modules.bettersession.model.SessionAid;
import play.mvc.Controller;

import java.util.List;

public class BetterSessionController extends Controller {

    public static void clearSession() {
//        Cache.clear();
        session.clear();
        clearCacheSession();
        SessionAidEvents.notifyClearElements();
        Logger.debug("===== Session is clear now =====");
        redirect("/");
    }

    private static void clearCacheSession() {
        // -----
        // Check if the session is still alive and in doesn't remove that session from Table
        List<SessionAid> sessionAidList = SessionAidEvents.getAllElements();
        if (!sessionAidList.isEmpty()) {
            for (SessionAid sessionAid : sessionAidList) {
                String key = sessionAid.getKey_name();
                if (Cache.get(key) != null) {
                    CacheSessionEvents.deleteSessionFromCache(key);
                }
            }
        }
    }

}
