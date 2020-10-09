package app.admin.controller;

import app.admin.payload.AdminResponse;
import app.admin.service.AdminService;
import app.common.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api")
@RequiredArgsConstructor
public class AdminController {

  public final AdminService adminService;

  @PostMapping("/users/get-all")
  public ResponseEntity<ApiResponse> getAll() {
    return ResponseEntity.ok(
        adminService.getAllUsers()
    );
  }
}
