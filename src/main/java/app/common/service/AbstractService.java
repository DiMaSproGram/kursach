package app.common.service;

import app.common.entity.AbstractEntity;
import app.common.repository.AbstractRepository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<E extends AbstractEntity, R extends AbstractRepository<E>> implements IService<E> {
  protected final R repository;

  public AbstractService(R repository) {
    this.repository = repository;
  }

  @Override
  public Optional<E> getById(int id) {
    return repository.findById(id);
  }

  @Override
  public List<E> findAll() {
    return repository.findAll();
  }

  @Override
  public E save(E e) {
    return repository.save(e);
  }

  @Override
  public void delete(int id) {
    repository.deleteById(id);
  }
}
