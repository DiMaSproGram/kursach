package app.entity;

import lombok.Data;

import javax.persistence.*;
import java.awt.image.BufferedImage;

@Data
@Entity
@Table(name = "Motherboard")
public class Motherboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private double price;

    private String image;

    public Motherboard() {

    }
    public Motherboard(String name, double price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }
}
