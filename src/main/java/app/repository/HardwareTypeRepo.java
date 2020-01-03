package app.repository;

import app.entity.Hardware;
import app.entity.HardwareType;
import org.springframework.data.repository.CrudRepository;

public interface HardwareTypeRepo extends CrudRepository<HardwareType, Integer> {
}
