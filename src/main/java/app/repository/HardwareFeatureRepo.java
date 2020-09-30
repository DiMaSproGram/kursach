package app.repository;

import app.common.repository.AbstractRepository;
import app.entity.HardwareEntity;
import app.entity.HardwareFeature;
import org.springframework.data.repository.CrudRepository;

public interface HardwareFeatureRepo extends AbstractRepository<HardwareFeature> {
  HardwareFeature findByHardwareEntityIdAndName(int hardwareEntity, String name);
  HardwareFeature findByHardwareEntityId(int hardwareEntity);
  Iterable<HardwareFeature> findAllByName(String name);

  Iterable<HardwareFeature> findAllByNameAndValue(String name, String value);
}
