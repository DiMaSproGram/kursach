package app.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Setter;

@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Page {
  private LastPage lastPage;

  public Page(@JsonProperty("page") LastPage lastPage) {
    this.lastPage = lastPage;
  }

  public String getLastPage() {
    return lastPage.last;
  }

  @Override
  public String toString() {
    return "Page{" +
        "lastPage=" + getLastPage() +
        '}';
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class LastPage {
    private String last;

    public LastPage(@JsonProperty("last") String last) {
      this.last = last;
    }
  }
}
