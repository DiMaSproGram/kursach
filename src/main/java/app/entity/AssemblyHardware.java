package app.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class AssemblyHardware {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
