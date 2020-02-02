package app.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "my_assemble")
public class Assembly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "my_user_id")
    private User user;

    private double totalPrice;

    public Assembly(){}
    public Assembly(User user, double totalPrice) {
//        this.assemblyHardware = assemblyHardware;
        this.user = user;
        this.totalPrice = totalPrice;
    }
}
