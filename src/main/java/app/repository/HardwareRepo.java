package app.repository;

import app.entity.HardwareEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface HardwareRepo extends CrudRepository<HardwareEntity, Integer> {
  HardwareEntity findByName(String name);

  @Transactional
  @Modifying
  @Query("update HardwareEntity hard set hard.price = :price WHERE hard.id = :hardwareId")
  void setHardwarePrice(@Param("hardwareId") int id, @Param("price") double price);
}
