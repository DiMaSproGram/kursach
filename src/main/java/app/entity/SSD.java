package app.entity;

import lombok.Data;

import javax.persistence.*;
import java.awt.image.BufferedImage;

@Data
@Entity
@Table(name = "SSD")
public class SSD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private double price;

    private String image;

    public SSD() {

    }
    public SSD(String name, double price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }
}
