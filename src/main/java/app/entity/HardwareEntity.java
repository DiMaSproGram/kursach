package app.entity;

import app.common.entity.AbstractEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Set;

@Getter
@Setter
@Entity
public class HardwareEntity extends AbstractEntity {

    private String name;
    private String description;
    private double price;
    private String image;
    private String link;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "hardware_type_id")
    private HardwareType type;

    public HardwareEntity() {
    }
    public HardwareEntity(String name, String description, double price, String image, String link, HardwareType type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.link = link;
        this.type = type;
    }
}
