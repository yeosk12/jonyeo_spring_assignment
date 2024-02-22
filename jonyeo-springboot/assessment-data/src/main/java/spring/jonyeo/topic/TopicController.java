package spring.jonyeo.topic;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topics")
public class TopicController {
	
	private static final Logger logger = LoggerFactory.getLogger(TopicController.class);
	
	@Autowired
	private TopicService topicService;

	@GetMapping
	public ResponseEntity<List<Topic>> getAllTopics(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Topic> topicsPage = topicService.getAllTopics(pageable);
		
		List<Topic> topics = topicsPage.getContent();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("X-Total-Count", String.valueOf(topicsPage.getTotalElements()));

		logger.info("GET /topics?page={}&size={}", page, size);
		
		return new ResponseEntity<>(topics, responseHeaders, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getTopic(@PathVariable Long id) {
	    logger.info("GET /topics/{}", id);
	    try {
	        Topic topic = topicService.getTopic(id);
	        logger.info("Response: {}", topic);
	        return ResponseEntity.ok(topic);
	    } catch (Exception e) {
	        logger.error("Error getting topic: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting topic: " + e.getMessage());
	    }
	}
	
	@PostMapping
	public ResponseEntity<String> addTopic(@RequestBody Map<String, Object> requestBody) {
	    Topic topic = new Topic();
	    topic.setName((String) requestBody.get("name"));
	    topic.setDescription((String) requestBody.get("description"));
	    String lessonName = (String) requestBody.get("lessonName");

	    logger.info("POST /topics - Request Body: {}", topic);
	    try {
	        topicService.addTopic(topic, lessonName);
	        return ResponseEntity.ok("Topic added successfully");
	    } catch (Exception e) {
	        logger.error("Error adding topic: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding topic: " + e.getMessage());
	    }
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateTopic(@RequestBody Topic topic, @PathVariable Long id) {
	    logger.info("PUT /topics/{} - Request Body: {}", id, topic);
	    try {
	        topicService.updateTopic(id, topic);
	        return ResponseEntity.ok("Topic updated successfully");
    	} catch (Exception e) {
    		logger.error("Error updating topic: " + e.getMessage());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating topic: " + e.getMessage());
    	}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTopic(@PathVariable Long id) {
	    logger.info("DELETE /topics/{}", id);
	    try {
	        topicService.deleteTopic(id);
	        return ResponseEntity.ok("Topic deleted successfully");
    	} catch (Exception e) {
    		logger.error("Error deleting topic: " + e.getMessage());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting topic: " + e.getMessage());
    	}
	}
	
	@GetMapping("/ext/{id}")
	public ResponseEntity<?> getTopicWithExtDetails(@PathVariable Long id) {
	    logger.info("GET /topics/ext/{}", id);
	    try {
	        ExternalDTO topic = topicService.getTopicWithExtDetails(id);
	        logger.info("Response: {}", topic);
	        return ResponseEntity.ok(topic);
	    } catch (Exception e) {
	        logger.error("Error getting topic: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting topic with ext details: " + e.getMessage());
	    }
	}
	
}
