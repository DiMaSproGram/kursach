package app.admin.service;

import app.admin.payload.AdminResponse;
import app.data.UserData;
import app.entity.User;
import app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class AdminService {

  public final UserService userService;

  public AdminResponse getAllUsers() {
    AdminResponse adminResponse = new AdminResponse();
    ArrayList<User> userList = (ArrayList<User>) userService.findAll();
    ArrayList<UserData> userDataList = new ArrayList<>();

    userList.forEach(
        user -> userDataList.add(
            new UserData(user)
        )
    );
    adminResponse.setList(userDataList);

    return adminResponse;
  }

}
