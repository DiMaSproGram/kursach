package app.payload;

import lombok.Data;

@Data
public class Pagination {
  private String href;
  private int pageNum;

  public Pagination(String href, int pageNum) {
    this.href = href;
    this.pageNum = pageNum;
  }
}
