package app.data;

import app.entity.Role;
import app.entity.User;
import lombok.Data;

import java.util.Set;

@Data
public class UserData {
  public String username;
  private Set<Role> roles;

  public UserData(User user) {
    this.username = user.getUsername();
    this.roles = user.getRoles();
  }
}
