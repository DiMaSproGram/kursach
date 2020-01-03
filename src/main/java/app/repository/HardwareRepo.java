package app.repository;

import app.entity.Assembly;
import app.entity.Hardware;
import org.springframework.data.repository.CrudRepository;

public interface HardwareRepo extends CrudRepository<Hardware, Integer> {
}
