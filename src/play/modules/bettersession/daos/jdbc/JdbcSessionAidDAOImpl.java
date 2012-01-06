/**
 * Author: Omar García
 * Date: 21/10/11
 * Time: 03:43 PM
 */
package play.modules.bettersession.daos.jdbc;

import play.Logger;
import play.modules.bettersession.daos.SessionAidDAO;
import play.modules.bettersession.model.SessionAid;
import play.modules.bettersession.utilities.JdbcUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcSessionAidDAOImpl implements SessionAidDAO {

    public boolean insertSessionAid(SessionAid sessionAid) {
        try {
            Connection conn = JdbcUtility.obtainConnection();

            // This must be validated outside
//            PreparedStatement insert = conn.prepareStatement("INSERT INTO session_aid VALUES(?, ?, ?)");
            PreparedStatement insert = conn.prepareStatement("INSERT INTO session_aid VALUES(?, ?)");
            insert.setString(1, sessionAid.getKey_name());
            insert.setString(2, sessionAid.getValue_name());
//            insert.setString(3, sessionAid.getTtl());

            insert.executeUpdate();

            conn.close();

        } catch (ClassNotFoundException e) {
            Logger.debug("ClassNotFoundException");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.debug("SQLException");
            return false;
        }

        return true;
    }

    public boolean updateSessionAid(SessionAid sessionAid) {
        try {
            Connection conn = JdbcUtility.obtainConnection();

            if (selectSessionAid(sessionAid.getKey_name()) != null) {

//                PreparedStatement update = conn.prepareStatement("UPDATE session_aid SET value_name = ?, ttl = ? WHERE key_name = ?");
                PreparedStatement update = conn.prepareStatement("UPDATE session_aid SET value_name = ? WHERE key_name = ?");
                update.setString(1, sessionAid.getValue_name());
//                update.setString(2, sessionAid.getTtl());
//                update.setString(3, sessionAid.getKey_name());
                update.setString(2, sessionAid.getKey_name());

                update.execute();

            } else {
                return false;
            }
            conn.close();

        } catch (ClassNotFoundException e) {
            Logger.debug("ClassNotFoundException");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.debug("SQLException");
            return false;
        }

        return true;
    }

    public boolean deleteSessionAid(String key) {
        try {
            Connection conn = JdbcUtility.obtainConnection();

            if (selectSessionAid(key) != null) {

                PreparedStatement delete = conn.prepareStatement("DELETE FROM session_aid WHERE key_name = ?");
                delete.setString(1, key);

                delete.executeUpdate();

            } else {
                return false;
            }
            conn.close();

        } catch (ClassNotFoundException e) {
//            Logger.debug("ClassNotFoundException");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
//            Logger.debug("SQLException");
            return false;
        }

        return true;
    }

    public boolean deleteAllSessionAid() {
        try {
            Connection conn = JdbcUtility.obtainConnection();

            PreparedStatement deleteAll = conn.prepareStatement("DELETE FROM session_aid");

//            deleteAll.executeUpdate();
            deleteAll.execute();

            conn.close();

        } catch (ClassNotFoundException e) {
//            Logger.debug("ClassNotFoundException");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
//            Logger.debug("SQLException");
            return false;
        }

        return true;
    }

    public SessionAid selectSessionAid(String key) {
        SessionAid sessionAid = null;

        try {
            Connection conn = JdbcUtility.obtainConnection();

            // Build the statement
            PreparedStatement select = conn.prepareStatement("SELECT * FROM session_aid WHERE key_name = ?");
            select.setString(1, key);
            // -----

            // Interact with the database.
            ResultSet rs = select.executeQuery();

            if (rs.next()) {
                sessionAid = new SessionAid();
                sessionAid.setKey_name(rs.getString("key_name"));
                sessionAid.setValue_name(rs.getString("value_name"));
//                sessionAid.setTtl(rs.getString("ttl"));
            }

            rs.close();
            conn.close();
        } catch (ClassNotFoundException e) {
//            Logger.debug("ClassNotFoundException");
        } catch (SQLException e) {
            e.printStackTrace();
//            Logger.debug("SQLException");
        }

        return sessionAid;
    }

    public List<SessionAid> selectAllSessionAid() {
        List<SessionAid> sessionAidList = new ArrayList<SessionAid>();
        try {
            Connection conn = JdbcUtility.obtainConnection();

            // Build the statement
            PreparedStatement selectAll = conn.prepareStatement("SELECT * FROM session_aid");
            // -----

            // Interact with the database.
            ResultSet rs = selectAll.executeQuery();
            while (rs.next()) {
                SessionAid sessionAid = new SessionAid();
                sessionAid.setKey_name(rs.getString("key_name"));
                sessionAid.setValue_name(rs.getString("value_name"));
//                sessionAid.setTtl(rs.getString("ttl"));
                sessionAidList.add(sessionAid);
            }

            rs.close();
            conn.close();
        } catch (ClassNotFoundException e) {
//            Logger.debug("ClassNotFoundException");
        } catch (SQLException e) {
            e.printStackTrace();
//            Logger.debug("SQLException");
            return null;
        }

        return sessionAidList;
    }
}
