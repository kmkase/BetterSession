/**
 * Author: Omar García
 * Date: 26/10/11
 * Time: 03:37 PM
 */
package controllers;

import models.User;
import play.modules.bettersession.BetterSession;
import play.mvc.Controller;
import play.mvc.With;

@With(SecureController.class)
public class SessionController extends Controller {

    public static void welcome() {
        User user = null;
        if (BetterSession.isConnected()) {
            user = User.findByUsername(BetterSession.connected());
        }
        render(user);
    }

    public static void betterSessionActivate(User user) {
        render(user);
    }

    public static void betterSessionDeactivate(User user) {
        render(user);
    }

}
