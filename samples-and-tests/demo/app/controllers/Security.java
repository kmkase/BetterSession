/**
 * Author: omar
 * Date: 26/10/11
 * Time: 03:16 PM
 */
package controllers;

import models.User;
import play.Logger;
import play.modules.bettersession.BetterSession;

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

    static void onDisconnected() {

        ApplicationController.index();
    }

    static boolean isConnected() {
        return BetterSession.isConnected();
    }

    static String connected() {
        return BetterSession.getSessionValue();
    }

    static long connectedAt() {
        return BetterSession.connectedAt();
    }

}
