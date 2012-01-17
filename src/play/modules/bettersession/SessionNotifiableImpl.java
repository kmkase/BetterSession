/**
 * Author: Omar García
 * Date: 18/10/11
 * Time: 10:24 AM
 */
package play.modules.bettersession;

import play.Logger;
import play.cache.Cache;
import play.modules.bettersession.daos.jdbc.JdbcSessionAidDAOImpl;
import play.modules.bettersession.daos.SessionAidDAO;
import play.modules.bettersession.handlers.SessionNotifiable;
import play.modules.bettersession.model.SessionAid;

import java.util.List;

/**
 * This class have the basic functions of create, delete, update and read Model session
 */
public class SessionNotifiableImpl implements SessionNotifiable {

    private final static SessionAidDAO sessionAidDAO = new JdbcSessionAidDAOImpl();

    public boolean notifyElementStored(String key, String value) {
        // -----
        // Fill the Object POJO and store into Table
        SessionAid sessionAid = new SessionAid();
        sessionAid.setKey_name(key);
        sessionAid.setValue_name(value);
//        sessionAid.setTtl(ttl);
//        Logger.debug("%n~~~~~ Saved into MODEL key: [%s], value: [%s] ~~~~~", key, value);
        return sessionAidDAO.insertSessionAid(sessionAid);
    }

    public boolean notifyElementRemoved(String key) {
        // -----
        // Remove the Object from Table
//        Logger.debug("%n~~~~~ Deleted from MODEL key: [%s] ~~~~~", key);
        return sessionAidDAO.deleteSessionAid(key);
    }

    public boolean notifyElementUpdated(String key, String value) {
        // -----
        // Update the Object POJO and store into Table
        SessionAid sessionAid = sessionAidDAO.selectSessionAid(key);
        sessionAid.setValue_name(value);
//        sessionAid.setTtl(ttl);
//        Logger.debug("%n~~~~~ Updated into MODEL key: [%s], value: [%s] ~~~~~", key, value);
        return sessionAidDAO.updateSessionAid(sessionAid);
    }

    public boolean notifyClearElements() {
        return sessionAidDAO.deleteAllSessionAid();
    }

    public void notifyElementExpired() {
        // -----
        // Check if the session is still alive and in doesn't remove that session from Table
        List<SessionAid> sessionAidList = getAllElements();
        if (!sessionAidList.isEmpty()) {
            for (SessionAid sessionAid : sessionAidList) {
                String key = sessionAid.getKey_name();
                if (Cache.get(key) == null) {
                    notifyElementRemoved(key);
                    Logger.debug("~~~~~ Session deleted from TABLE ~~~~~");
                }
            }
        }
    }

    public List<SessionAid> getAllElements() {
        // -----
        // Get all sessions from Table
        return sessionAidDAO.selectAllSessionAid();
    }

    public boolean existElement(String key) {
        // -----
        // Verify if a one session exists into Table
        return sessionAidDAO.selectSessionAid(key) != null;
    }

}
