package eu.accesa.learningplatform.model.dto;

public class MostPopularTrainerDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String imageUrl;
    private Long jobTitleId;
    private Double score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(Long jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
