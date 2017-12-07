package de.hska.twitterklon.redis.repositories;

import de.hska.twitterklon.api.transferobjects.PostDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<PostDto, String> {
}
