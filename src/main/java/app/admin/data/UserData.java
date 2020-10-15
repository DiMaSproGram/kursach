package app.admin.data;

import app.entity.Role;
import app.entity.User;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UserData {
  public String username;
  private Set<Role> roles;
  private Date dateCreated;
  private Date dateUpdated;

  public UserData(User user) {

    if (user.getDateCreated() != null) {
      this.dateCreated = user.getDateCreated();
    }
    if (user.getDateUpdated() != null) {
      this.dateUpdated = user.getDateUpdated();
    }
    this.username = user.getUsername();
    this.roles = user.getRoles();
  }
}
