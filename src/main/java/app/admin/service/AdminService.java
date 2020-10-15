package app.admin.service;

import app.admin.payload.AdminSearchRequest;
import app.admin.payload.GetAllUsersResponse;
import app.admin.data.UserData;
import app.entity.User;
import app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class AdminService {

  public final UserService userService;

  public GetAllUsersResponse getAllUsers() {
    GetAllUsersResponse getAllUsersResponse = new GetAllUsersResponse();
    ArrayList<User> userList = (ArrayList<User>) userService.findAll();
    ArrayList<UserData> userDataList = new ArrayList<>();

    userList.forEach(
        user -> userDataList.add(
            new UserData(user)
        )
    );
    getAllUsersResponse.setList(userDataList);

    return getAllUsersResponse;
  }

  public GetAllUsersResponse getAllUsersBySearching(AdminSearchRequest adminSearchRequest) {
    GetAllUsersResponse getAllUsersResponse = new GetAllUsersResponse();
    ArrayList<User> usersList = (ArrayList<User>) userService.getAllBySearching(
        adminSearchRequest.getUserName()
    );
    ArrayList<UserData> resultList = new ArrayList<>();

    usersList.forEach(
        el -> resultList.add(
            new UserData(el)
        )
    );
    getAllUsersResponse.setList(resultList);

    return getAllUsersResponse;
  }

}
