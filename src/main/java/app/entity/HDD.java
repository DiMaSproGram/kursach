package app.entity;

import lombok.Data;

import javax.persistence.*;
import java.awt.image.BufferedImage;

@Data
@Entity
@Table(name = "HDD")
public class HDD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private double price;

    private String image;

    public HDD() {

    }
    public HDD(String name, double price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }
}
