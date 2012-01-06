/**
 * Author: Omar García
 * Date: Jul 9, 2010
 * Time: 10:52:24 AM
 */

package play.modules.bettersession.utilities;

import play.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcUtility {

    // For Singleton pattern
	private volatile static JdbcUtility uniqueInstance;

    /**
     * Private Constructor for singleton pattern
     * NOTE: This default constructor shall be called after the constructor with parameter
     */
    private JdbcUtility() {

    }

    public static JdbcUtility getInstance() {

		if (uniqueInstance == null) {
			synchronized (JdbcUtility.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new JdbcUtility();
                }
			}
		}
		return uniqueInstance;
	}

    public static Connection obtainConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:db/h2/improvedsession", "session", "aid");
    }

    public void createSessionAidTable() {

        Connection conn = null;
        try {
            conn = obtainConnection();

            StringBuilder statement = new StringBuilder();


            statement.append("CREATE TABLE session_aid (");
            statement.append("key_name VARCHAR(100), ");
            statement.append("value_name VARCHAR(100)");
//            statement.append("ttl VARCHAR(10)");
            statement.append(");");

            // Interact with the database.
            PreparedStatement ps = conn.prepareStatement(statement.toString());
            ps.executeUpdate();

            conn.close();
        } catch (SQLException e) {
//            Logger.debug("BetterSession Table already exists...");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void init() {
        JdbcUtility.getInstance().createSessionAidTable();
    }

}
