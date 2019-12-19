package app.entity;

import lombok.Data;

import javax.persistence.*;
import java.awt.image.BufferedImage;

@Data
@Entity
@Table(name = "Cooler")
public class Cooler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private double price;

    private String image;

    public Cooler() {

    }
    public Cooler(String name, double price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }
}
