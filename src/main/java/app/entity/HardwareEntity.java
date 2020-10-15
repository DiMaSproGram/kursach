package app.entity;

import app.common.entity.AbstractEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
public class HardwareEntity extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "image")
    private String image;

    @Column(name = "link")
    private String link;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "hardware_type_id", referencedColumnName = "id")
    private HardwareType type;

    public HardwareEntity() {
    }

    public HardwareEntity(
        String name,
        String description,
        double price,
        String image,
        String link,
        HardwareType type,
        Date currentDate
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.link = link;
        this.type = type;
        setDateCreated(currentDate);
        setDateUpdated(currentDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HardwareEntity that = (HardwareEntity) o;
        return Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(type, that.type);
    }
}
