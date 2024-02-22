package spring.jonyeo.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicRepository extends JpaRepository<Topic, Long> {
	
	@Query("SELECT DISTINCT t FROM Topic t LEFT JOIN FETCH t.lessons")
	Page<Topic> findAll(Pageable pageable);
}
