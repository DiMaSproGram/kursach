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
public class AssemblyHardware extends AbstractEntity {

  @ManyToOne(cascade = {CascadeType.MERGE})
//  @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
  @JoinColumn(name = "assemble_id", referencedColumnName = "id")
  private Assembly assembly;

  @ManyToOne(cascade = {CascadeType.MERGE})
//  @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
  @JoinColumn(name = "hardware_id", referencedColumnName = "id")
  private HardwareEntity hardwareEntity;

  public AssemblyHardware() {
  }

  public AssemblyHardware(Assembly assembly, HardwareEntity hardwareEntity, Date currentDate) {
    this.assembly = assembly;
    this.hardwareEntity = hardwareEntity;
    setDateCreated(currentDate);
    setDateUpdated(currentDate);
  }
}
