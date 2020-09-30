package app.entity;

import app.common.entity.AbstractEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "my_assemble")
public class Assembly extends AbstractEntity {

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "my_user_id")
    private User user;

    private double totalPrice;

    public Assembly(){}
    public Assembly(User user, double totalPrice) {
        this.user = user;
        this.totalPrice = totalPrice;
    }
}
