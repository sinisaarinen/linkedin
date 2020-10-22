/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.entitles.Endorsement;
import projekti.entitles.Photo;
import projekti.entitles.Skill;
import projekti.entitles.User;
import projekti.repositories.ConnectionRepository;
import projekti.repositories.EndorsementRepository;
import projekti.repositories.PhotoRepository;
import projekti.repositories.SkillRepository;
import projekti.repositories.UserRepository;
/**
 *
 * @author saasini
 */
@Service
public class UserService {
    
    @Autowired
    private PhotoRepository photoRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SkillRepository skillRepository;
    
    @Autowired
    private EndorsementRepository endorsementRepository;
    
    public boolean signUp(String username, String password, String fullname, String profilename) {
        User user = new User(username, password, fullname, profilename, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        userRepository.save(user);
        return true;
    }
    
    public boolean signUp(User user) {
        userRepository.save(user);
        return true;
    }
    
    public boolean addPhoto(User owner, byte[] content) {
        Photo photo = new Photo(owner, content);
        photoRepository.save(photo);
        return true;
    }
    
    //public User findUser(String name) {
        //User user = userRepository.findByName(name);
        //return user;
    //}
    
    public boolean addSkill(String name, User user) {
        Skill skill = new Skill(name, user, new ArrayList<>());
        skillRepository.save(skill);
        return true;
    }
    
    public boolean deleteSkill(Skill skill) {
        skillRepository.delete(skill);
        return true;
    }
    
    public boolean addEndorsement(User endorser, Skill skill) {
        Endorsement endorsement = new Endorsement(endorser, skill);
        endorsementRepository.save(endorsement);
        return true;
    }
    
    public boolean deleteEndorsement(Endorsement endorsement) {
        endorsementRepository.delete(endorsement);
        return true;
    }
}
