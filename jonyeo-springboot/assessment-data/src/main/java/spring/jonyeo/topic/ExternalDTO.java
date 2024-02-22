package spring.jonyeo.topic;

public class ExternalDTO {

    private Long id;
    private String name;
    private String description;
    private Lesson lessons;
    private String extDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Lesson getLessons() {
        return lessons;
    }

    public void setLessons(Lesson lesson) {
        this.lessons = lesson;
    }

    public String getExtDetails() {
        return extDetails;
    }

    public void setExtDetails(String extDetails) {
        this.extDetails = extDetails;
    }
}
