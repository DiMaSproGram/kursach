package app.common.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

  private boolean success;

//  @JsonInclude(JsonInclude.Include.NON_NULL)
//  private Error error;

//  @Data
//  public static class Error {
//    int code;
//    String message;
//
//    public Error(ErrorCode errorCode){
//      this.code = errorCode.code;
//      this.message = errorCode.message;
//    }
//  }
}
