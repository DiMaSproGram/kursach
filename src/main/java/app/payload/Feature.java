package app.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.TreeSet;

@Data
public class Feature {

  String featureName;
  TreeSet<String> featureValues;

  @JsonCreator
  public Feature(
      @JsonProperty("featureName") String featureName,
      @JsonProperty("featureValues") TreeSet<String> featureValues
  ) {
    this.featureName = featureName;
    this.featureValues = featureValues;
  }
}
