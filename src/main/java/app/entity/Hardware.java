package app.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Hardware {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private double price;

    private String image;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "hardware_type_id")
    private HardwareType type;


    public Hardware() {
    }
    public Hardware(String name, double price, String image, HardwareType type) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.type = type;
    }
}