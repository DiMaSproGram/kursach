package app.entity;

import app.common.entity.AbstractEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
public class HardwareType extends AbstractEntity {

    @Column(name = "name")
    private String name;

    public HardwareType() {
    }

    public HardwareType(String name, Date dateCreated) {
        this.name = name;
        setDateCreated(dateCreated);
        setDateUpdated(dateCreated);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HardwareType that = (HardwareType) o;
        return Objects.equals(name, that.name);
    }

}
