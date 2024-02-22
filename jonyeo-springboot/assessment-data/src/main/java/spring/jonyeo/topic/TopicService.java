package spring.jonyeo.topic;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TopicService {
	
	@Autowired
	private TopicRepository topicRepository;
	
	@Autowired
	private LessonRepository lessonRepository;
	
    @Autowired
    private ExternalApiService externalApiService;
	
	
	@Transactional(readOnly = true)
	public Page<Topic> getAllTopics(Pageable pageable) {
	    return topicRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Topic getTopic(Long id) {
		Topic topic = topicRepository.findById(id)
	            .orElseThrow(() -> new NoSuchElementException("Topic not found with ID: " + id));
		
		return topic;
	}
	
// To test Transactional that if lesson has error, the topic won't be save to DB.
	@Transactional 
	public void addTopic(Topic topic, String lessonName) {
	    topicRepository.save(topic);

	    Lesson lesson = new Lesson();
	    lesson.setName(lessonName);
	    lesson.setTopic(topic);
	    lessonRepository.save(lesson);
	}

	@Transactional
	public void updateTopic(Long id, Topic newTopic ) {
	    Optional<Topic> existingTopicOptional = topicRepository.findById(id);
	    if (existingTopicOptional.isPresent()) {
	        Topic existingTopic = existingTopicOptional.get();
	        existingTopic.setName(newTopic.getName());
	        existingTopic.setDescription(newTopic.getDescription());
	        
	        Lesson existingLesson = existingTopic.getLessons();
	        existingLesson.setName(newTopic.getLessons().getName());
	        
	        topicRepository.save(existingTopic);
	    } else {
	        throw new NoSuchElementException("Topic not found with ID: " + id);
	    }
	}

	@Transactional
	public void deleteTopic(Long id) {
	    if (topicRepository.existsById(id)) {
	        topicRepository.deleteById(id);
	    } else {
	        throw new NoSuchElementException("Topic not found with ID: " + id);
	    }
	}
	
	// To test calling from external api
	@Transactional(readOnly = true)
	public ExternalDTO getTopicWithExtDetails(Long id) {
	    Topic topic = topicRepository.findById(id)
	            .orElseThrow(() -> new NoSuchElementException("Topic not found with ID: " + id));
	
	    ExternalDTO dto = new ExternalDTO();
	    dto.setId(topic.getId());
	    dto.setName(topic.getName());
	    dto.setDescription(topic.getDescription());
	    
	    Map<String, Object> extDetails = externalApiService.getExtDetails(id);
	    dto.setExtDetails(externalApiService.extractDetails(extDetails));

	    dto.setLessons(topic.getLessons());
	    
	    return dto;
	}
	
	
}
