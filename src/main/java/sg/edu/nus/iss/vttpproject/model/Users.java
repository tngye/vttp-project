package sg.edu.nus.iss.vttpproject.model;

import org.springframework.util.MultiValueMap;

public class Users {
    private String username;
    private String password;
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public static Users convert(MultiValueMap<String, String> form) {
        Users user = new Users();
        user.setUsername(form.getFirst("username"));
        user.setPassword(form.getFirst("password"));
        return user;
    }
    
}
