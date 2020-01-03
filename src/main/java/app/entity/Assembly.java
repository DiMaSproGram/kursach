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

    @OneToMany
    @JoinColumn(name = "hardware_id")
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "assembly_hardware", joinColumns = @JoinColumn(name = "assembly_id"), inverseJoinColumns = @JoinColumn(name = "hardware_id"))
    @OrderColumn(name="hardware_index")
    private Hardware[] hardware;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "my_user_id")
    private User user;

    private double totalPrice;

    public Assembly(){}
    public Assembly(Hardware[] hardware, User user, double totalPrice) {
        this.hardware = hardware;
        this.user = user;
        this.totalPrice = totalPrice;
    }
}
