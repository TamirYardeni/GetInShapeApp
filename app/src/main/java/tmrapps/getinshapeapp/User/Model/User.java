package tmrapps.getinshapeapp.User.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by tamir on 2/3/2018.
 * This is the user object that lives in the app
 * It contains the google user id and the type of the user (regular or admin)
 */
@Entity
public class User {
    @PrimaryKey
    @NonNull
    public String id;

    public int roleType;

    public User() {

    }

    /**
     * Create user with data
     * @param id
     * @param roleType
     */
    public User(String id, int roleType) {
        this.id = id;
        this.roleType = roleType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public int getRoleType() { return this.roleType;}

    public String getId() { return this.id; }

}
