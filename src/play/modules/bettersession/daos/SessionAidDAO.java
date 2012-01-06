/**
 * Author: Omar García
 * Date: 19/10/11
 * Time: 12:29 PM
 */
package play.modules.bettersession.daos;

import play.modules.bettersession.model.SessionAid;

import java.util.List;

public interface SessionAidDAO {

    public abstract boolean insertSessionAid(SessionAid sessionAid);

    public abstract boolean updateSessionAid(SessionAid sessionAid);

    public abstract boolean deleteSessionAid(String key);

    public abstract boolean deleteAllSessionAid();

    public abstract SessionAid selectSessionAid(String key);

    public abstract List<SessionAid> selectAllSessionAid();

}
