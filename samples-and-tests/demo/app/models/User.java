/**
 * Author: omar
 * Date: 26/10/11
 * Time: 03:06 PM
 */
package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends Model {

    public String username;

    public String password;

    public static User findByUsername(String username) {
        return User.find("byUsername", username).first();
    }
}
