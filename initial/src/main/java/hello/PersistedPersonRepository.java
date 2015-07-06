package hello;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersistedPersonRepository extends CrudRepository<PersistedPerson, Long> {
	
}
