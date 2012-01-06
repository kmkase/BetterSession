/**
 * Author: Omar García
 * Date: 4/11/11
 * Time: 10:20 AM
 */
package play.modules.bettersession.utilities;

import play.Logger;
import play.libs.Crypto;
import play.mvc.Http;
import play.mvc.Scope;

import java.util.Date;

/**
 * Utilities for Cookie and SessionCookie to Sign them
 */
public class CookieSessionUtility {
    
    private static final String SIGN_KEY = "sign_value";
    private static final String DATE_KEY = "date_value";


    public static void putSession(String key, String value) {
        Scope.Session session = Scope.Session.current();
//        session.put(key, signValue(value));
        // -----

        // NEW
//        session.put(key, storeDateIntoSession(signValue(value), new Date()));

        // Better NEW
        // Separate value, signValue and Date
        session.put(key, value);
        session.put(String.format("%s_%s", key, SIGN_KEY), signValue(value));
        session.put(String.format("%s_%s", key, DATE_KEY), new Date().getTime());
    }

    public static boolean containsSession(String key) {
        Scope.Session session = Scope.Session.current();
//        return session.contains(key);
        // Better NEW
        return session.contains(key) && session.contains(String.format("%s_%s", key, SIGN_KEY));
    }

    public static String getSessionValue(String key) {
        Scope.Session session = Scope.Session.current();
//        return unSignValue(session.get(key));
        // -----

        // NEW
//        String signValue = getValueFromSession(session.get(key));
//        return unSignValue(signValue);

        // Better NEW
        String value = session.get(key);
        String signValue = session.get(String.format("%s_%s", key, SIGN_KEY));
        return verifySignValue(value, signValue);
    }

    public static long getDateOfSession(String key) {
        Scope.Session session = Scope.Session.current();
//        return getDateFromSession(session.get(key));
        
        // Better NEW
        return Long.parseLong(session.get(String.format("%s_%s", key, DATE_KEY)));
    }

//    public static void putSession(String key, String value) {
//        Scope.Session session = Scope.Session.current();
//        session.put(key, signValue(value));
//    }

//    public static boolean containsSession(String key) {
//        Scope.Session session = Scope.Session.current();
//        return session.contains(key);
//    }

//    public static String getSessionValue(String key) {
//        Scope.Session session = Scope.Session.current();
//        return unSignValue(session.get(key));
//    }

    public static void removeSession() {
        Scope.Session session = Scope.Session.current();
        session.clear();
    }

    public static void makeCookie(String key, String value, String ttl) {
        Http.Response response = Http.Response.current();
//        response.setCookie(key, signValue(value), ttl);
        // Better NEW
//        Logger.debug("%n~~~~~ COOKIE created key: [%s], value: [%s] ~~~~~", key, value);
        response.setCookie(key, value, ttl);
        response.setCookie(signKey(key), signValue(value), ttl);
    }

    public static String getCookie(String key) {
        Http.Request request = Http.Request.current();
        Http.Cookie cookie = request.cookies.get(key);
        Http.Cookie signCookie = request.cookies.get(signKey(key));
//        if (cookie != null) {
        if (cookie != null && signCookie != null) {
            // NEW
//            return unSignValue(cookie.value);
            // Better NEW
            return verifySignValue(cookie.value, signCookie.value);
        }
        return null;
    }

    public static void removeCookie(String key) {
        Http.Response response = Http.Response.current();
        response.removeCookie(key);
        // Better NEW
        response.removeCookie(signKey(key));
    }

    @Deprecated
    private static String storeDateIntoSession(String value, Date date) {
        return String.format("%s:%s", value, date.getTime());
    }

    @Deprecated
    private static long getDateFromSession(String value) {
        long date = 0;
        if (value != null && value.indexOf(":") > 0) {
//            String str = value.substring(0, value.indexOf(":"));
            date = Long.parseLong(value.substring(value.indexOf(":") + 1));
        }
        return date;
    }

    @Deprecated
    private static String getValueFromSession(String value) {
        String str = null;
        if (value != null && value.indexOf(":") > 0) {
            str = value.substring(0, value.indexOf(":"));
//            date = Long.parseLong(value.substring(value.indexOf(":") + 1));
        }
        return str;
    }

    private static String signKey(String key) {
        return String.format("sign_%s", key);
    }

    private static String signValue(String value) {
//        return String.format("%s-%s", Crypto.sign(value), value);
        // Better NEW
        return Crypto.sign(value);
    }

    @Deprecated
    private static String unSignValue(String signValue) {
        if (signValue != null && signValue.indexOf("-") > 0) {
            String sign = signValue.substring(0, signValue.indexOf("-"));
            String value = signValue.substring(signValue.indexOf("-") + 1);
            if(Crypto.sign(value).equals(sign)) {
                return value;
            }
        }
        return null;
    }
    
    private static String verifySignValue(String value, String signValue) {
        if(value != null && signValue != null) {
            if(Crypto.sign(value).equals(signValue)) {
//                Logger.debug("value and signValue are equals");
                return value;
            }
        }
        return null;
        
    }
    
}
