/**
 * Author: Omar García
 * Date: 18/10/11
 * Time: 06:03 PM
 */
package play.modules.bettersession.handlers;

import play.modules.bettersession.model.SessionAid;

import java.util.List;

public interface SessionNotifiable {

    public boolean notifyElementStored(String key, String value);

    public boolean notifyElementRemoved(String key);

    public boolean notifyElementUpdated(String key, String value);

    public boolean notifyClearElements();

    public void notifyElementExpired();

    public List<SessionAid> getAllElements();

    public boolean existElement(String key);

}
