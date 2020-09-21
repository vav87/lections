import com.fasterxml.jackson.annotation.JsonFormat;

public class User {
    @JsonFormat(pattern = "\\d{3}")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
