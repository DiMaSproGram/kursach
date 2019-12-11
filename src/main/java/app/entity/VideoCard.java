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
}
