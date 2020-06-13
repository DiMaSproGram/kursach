package app.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
public class HardwarePayload {
  private String name;
  private String description;
  private Prices price;
  private Images image;
  private String link;

  @JsonCreator
  public HardwarePayload(
      @JsonProperty("name") String name,
      @JsonProperty("description") String description,
      @JsonProperty("prices") Prices price,
      @JsonProperty("images") Images image,
      @JsonProperty("html_url") String link
  ) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.image = image;
    this.link = link;
  }

  @Override
  public String toString() {
    return "Hardware{" +
        "name='" + name + '\'' +
        ", price=" + price.priceMin.price +
        ", image=" + image.imageUrl +
        ", link='" + link + '\'' +
        '}';
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public double getPrice() {
    return Double.parseDouble(price.priceMin.price);
  }

  public String getImage() {
    return image.imageUrl;
  }

  public String getLink() {
    return link;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class Images {
    private String imageUrl;

    public Images(@JsonProperty("header") String imageUrl) {
      this.imageUrl = "https:" + imageUrl;
    }
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class Prices {
    private PriceMin priceMin;

    public Prices(@JsonProperty("price_min") PriceMin priceMin) {
      this.priceMin = priceMin;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class PriceMin {
      private String price;

      public PriceMin(@JsonProperty("amount") String price) {
        this.price = price;
      }
    }
  }
}
