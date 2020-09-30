package app.entity;

import app.common.entity.AbstractEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class AssemblyHardware extends AbstractEntity {

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "assembly_id")
    private Assembly assembly;

//    @ManyToOne(cascade = {CascadeType.MERGE)
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "hardware_id")
    private HardwareEntity hardwareEntity;

    public AssemblyHardware() {
    }
    public AssemblyHardware(Assembly assembly, HardwareEntity hardwareEntity) {
        this.assembly = assembly;
        this.hardwareEntity = hardwareEntity;
    }
}
