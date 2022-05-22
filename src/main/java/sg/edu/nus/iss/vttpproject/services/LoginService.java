package sg.edu.nus.iss.vttpproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import sg.edu.nus.iss.vttpproject.repository.LoginRepository;

@Service
public class LoginService {

    @Autowired
    private LoginRepository lRepo;

    public boolean addUser(MultiValueMap<String, String> form) {
        boolean added = lRepo.addUser(form);
        return added;
    }

    public boolean authenticateUser(String username, String password) {
        boolean authenticated = lRepo.authenticate(username, password);
        return authenticated;
    }
    
}
