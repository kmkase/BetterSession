/**
 * Author: omar
 * Date: 26/10/11
 * Time: 03:16 PM
 */
package controllers;

import models.User;
import play.Logger;

public class Security extends SecureController.Security {

    static boolean authenticate(String username, String password) {
        User user = User.findByUsername(username);
        if (user != null) {
            if (user.password.equals(password)) {
                Logger.debug("User AUTHENTICATED");
                return true;
            }
        }

        return false;
    }

    static void onAuthenticated() {
        SessionController.welcome();
    }

//    static void onDisconect() {
//        // -----
//        // Drop session cookie, uniqueSession from cache and remember cookie
//    }

    static void onDisconnected() {

        ApplicationController.index();
    }

}
