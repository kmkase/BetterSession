/**
 * Author: Omar García
 * Date: 7/10/11
 * Time: 10:48 AM
 */
package play.modules.bettersession.handlers;

public interface BetterSessionHandler {

    public void create(String value, Boolean remember);

    public void remove();

    public boolean exist(String value);

    public void renew();

    public boolean isRememberCookieCreated();

    public void renewSessionFromRememberCookie();

    public boolean isConnected();

    public String getSessionValue();

    public long getDateFromSession();

}
