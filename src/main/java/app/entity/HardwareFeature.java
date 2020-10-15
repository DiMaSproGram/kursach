package app.entity;

import app.common.entity.AbstractEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class HardwareFeature extends AbstractEntity {

  @Column(name = "name")
  private String name;

  @Column(name = "value")
  private String value;

  @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
  @JoinColumn(name = "hardware_entity_id", referencedColumnName = "id")
  private HardwareEntity hardwareEntity;

  public HardwareFeature() {
  }

  public HardwareFeature(String name, String value, HardwareEntity hardwareEntity, Date currentDate) {
    this.name = name;
    this.value = value;
    this.hardwareEntity = hardwareEntity;
    setDateCreated(currentDate);
    setDateUpdated(currentDate);
  }

}
