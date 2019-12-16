package app.entity;

import javax.persistence.*;
import java.awt.image.BufferedImage;

@Entity
@Table(name = "Cooler")
public class Cooler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private double price;

    private String image;

}
