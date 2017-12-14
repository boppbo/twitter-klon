package de.hska.twitterklon.redis.repositories;

import de.hska.twitterklon.redis.repositories.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
    default List<String> searchUser(String searchKey, int resultCount, int skipCount) {
        /* Elegant solution I can't use because of repositories :-(
        ArrayList<String> result = new ArrayList<>();
        Cursor<Map.Entry<String, UserEntity>> curser = hashOps.scan(ScanOptions.scanOptions()
                                                                               .match("%" + searchKey + "%")
                                                                               .build());
        for(int i = 0; i < resultCount && curser.hasNext(); i++) {
            result.add(curser.next().getKey());
        }
        return result;
        */

        return StreamSupport.stream(this.findAll().spliterator(), false)
                            .map(UserEntity::getUserName)
                            .filter(un -> un.contains(searchKey))
                            .sorted()
                            .skip(skipCount)
                            .limit(resultCount)
                            .collect(Collectors.toList());
    }
}
