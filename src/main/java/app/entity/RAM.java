package app.entity;

        import lombok.Data;

        import javax.persistence.*;
        import java.awt.image.BufferedImage;

@Data
@Entity
@Table(name = "RAM")
public class RAM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private double price;

    private String image;

    public RAM() {

    }
    public RAM(String name, double price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }
}
