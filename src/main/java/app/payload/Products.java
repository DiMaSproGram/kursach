package app.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Products {
  private List<HardwarePayload> products;

  public Products(@JsonProperty("products") List<HardwarePayload> products) {
    this.products = products;
  }
}
