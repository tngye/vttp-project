package sg.edu.nus.iss.vttpproject.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.vttpproject.model.Players;
import sg.edu.nus.iss.vttpproject.repository.PlayerRepository;
import sg.edu.nus.iss.vttpproject.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository uRepo;

    @Autowired
    private PlayerRepository pRepo;

    public List<Players> getFavourites(String username) {
        List<Integer> fav = uRepo.getFavourites(username);
        List<Players> favList = new LinkedList<>();
        for(Integer id: fav){
            Players player = pRepo.getPlayer(id);
            favList.add(player);
        }
        return favList;
    }

    public boolean addToFav(Integer id, String username) {
        return uRepo.addToFav(id, username);
    }

    public boolean checkFav(Integer id, String username){
        return uRepo.checkFav(id, username);
    }

    public boolean checkFavTeam(Integer id, String username) {
        return uRepo.checkFavTeam(id, username);
    }

    public boolean addToFavTeams(Integer id, String username) {
        return uRepo.addToFavTeam(id, username);
    }
    
}
