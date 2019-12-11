package app.repository;

import app.entity.VideoCard;
import org.springframework.data.repository.CrudRepository;

public interface VideoCardRepo extends CrudRepository<VideoCard, Integer> {

}
