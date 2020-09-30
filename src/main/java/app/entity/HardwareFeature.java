package app.entity;

import app.common.entity.AbstractEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class HardwareFeature extends AbstractEntity {

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
