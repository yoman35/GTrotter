package gtrotter.eu.android.models;

@SuppressWarnings("unused")
public class Mail {

    private String subject;
    private String name;
    private String description;
    private String email;
    private String email_team;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail_team() {
        return email_team;
    }

    public void setEmail_team(String email_team) {
        this.email_team = email_team;
    }

}
