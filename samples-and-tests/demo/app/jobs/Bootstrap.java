/**
 * Author: omar
 * Date: 26/10/11
 * Time: 04:15 PM
 */
package jobs;

import models.User;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {

    @Override
    public void doJob() {
        if (User.count() == 0) {
            Fixtures.loadModels("initial-data.yml");
            Logger.debug("initial-data loaded");
        }
    }
}
