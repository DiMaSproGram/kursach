package app.common.service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface IService<E> {

  Optional<E> getById(int id);

  List<E> findAll();

  E save(E e);

  void delete(int id);
}
