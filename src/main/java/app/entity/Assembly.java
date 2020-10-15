package app.entity;

import app.common.entity.AbstractEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "my_assemble")
public class Assembly extends AbstractEntity {

  @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
  @JoinColumn(name = "my_user_id", referencedColumnName = "id")
  private User user;

  @Column(name = "total_price")
  private double totalPrice;

  public Assembly() {
  }

  public Assembly(User user, double totalPrice, Date currentDate) {
    this.user = user;
    this.totalPrice = totalPrice;
    setDateCreated(currentDate);
    setDateUpdated(currentDate);
  }
}
