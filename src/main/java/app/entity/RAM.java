package app.entity;

        import javax.persistence.*;
        import java.awt.image.BufferedImage;

@Entity
@Table(name = "RAM")
public class RAM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private double price;

    private String image;

}
