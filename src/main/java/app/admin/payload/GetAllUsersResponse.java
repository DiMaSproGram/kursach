package app.admin.payload;

import app.common.payload.ApiResponse;
import app.admin.data.UserData;
import lombok.Data;

import java.util.ArrayList;

@Data
public class GetAllUsersResponse extends ApiResponse {
    ArrayList<UserData> list;
}
