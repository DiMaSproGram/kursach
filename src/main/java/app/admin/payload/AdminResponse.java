package app.admin.payload;

import app.common.payload.ApiResponse;
import app.data.UserData;
import app.entity.User;
import lombok.Data;

import java.util.ArrayList;

@Data
public class AdminResponse extends ApiResponse {
    ArrayList<UserData> list;
}
