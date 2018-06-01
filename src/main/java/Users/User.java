package Users;

public class User {
    private String eMail;
    private String token;

    public User(String eMail, String token){
        this.eMail = eMail;
        this.token = token;
    }

    public User(String eMail){
        this.eMail = eMail;
    }

    public String geteMail() {
        return eMail;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
