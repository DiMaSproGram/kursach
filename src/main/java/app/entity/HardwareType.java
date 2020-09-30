package app.entity;

import app.common.entity.AbstractEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class HardwareType extends AbstractEntity {

    private String name;

    public HardwareType() {
    }
    public HardwareType(String name) {
        this.name = name;
    }

}
