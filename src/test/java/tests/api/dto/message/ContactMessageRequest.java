package tests.api.dto.message;

public class ContactMessageRequest {

    private String first_name;
    private String last_name;
    private String email;
    private String subject;
    private String message;

    public ContactMessageRequest() {
    }

    public ContactMessageRequest(
            String first_name,
            String last_name,
            String email,
            String subject,
            String message
    ) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}