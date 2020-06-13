package app.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class HardwareFeature {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;
  private String value;

  @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
  @JoinColumn(name = "hardware_entity_id")
  private HardwareEntity hardwareEntity;

  public HardwareFeature() {
  }
  public HardwareFeature(String name, String value, HardwareEntity hardwareEntity) {
    this.name = name;
    this.value = value;
    this.hardwareEntity = hardwareEntity;
  }

}
