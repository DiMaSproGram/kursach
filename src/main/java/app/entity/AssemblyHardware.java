package app.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class AssemblyHardware {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "assembly_id")
    private Assembly assembly;

    @ManyToOne
    @JoinColumn(name = "hardware_id")
    private Hardware hardware;

    public AssemblyHardware() {
    }
    public AssemblyHardware(Assembly assembly, Hardware hardware) {
        this.assembly = assembly;
        this.hardware = hardware;
    }
}
