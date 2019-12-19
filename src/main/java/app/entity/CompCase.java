package app.entity;

import lombok.Data;

import javax.persistence.*;
import java.awt.image.BufferedImage;

@Data
@Entity
@Table(name = "CompCase")
public class CompCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private double price;

    private String image;

    public CompCase() {

    }
    public CompCase(String name, double price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }
}