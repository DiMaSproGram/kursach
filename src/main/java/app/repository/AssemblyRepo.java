package app.repository;

import app.entity.Assembly;
import org.springframework.data.repository.CrudRepository;

public interface AssemblyRepo extends CrudRepository<Assembly, Integer> {
}
