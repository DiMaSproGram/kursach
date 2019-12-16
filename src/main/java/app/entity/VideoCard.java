package app.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class VideoCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private double price;

    private String image;

    public VideoCard() {

    }
    public VideoCard(String name, double price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }
}
