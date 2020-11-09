/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projekti.entitles.Endorsement;
import projekti.entitles.Skill;
import projekti.entitles.User;
import projekti.repositories.ConnectionRepository;
import projekti.repositories.EndorsementRepository;
import projekti.repositories.SkillRepository;
import projekti.repositories.UserRepository;
/**
 *
 * @author saasini
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SkillRepository skillRepository;
    
    @Autowired
    private EndorsementRepository endorsementRepository;

    public User currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        return user;
    }
    
    public List<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();        
        return allUsers;
    }

    public boolean isLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());

        if (user == null) {
            return false;
        } else {
            return true;
        }
    }

    public Optional<User> getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    public User getByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }

    public boolean signUp(String username, String password, String fullname, String profilename) {
        User user = new User(username, password, fullname, profilename, null, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        userRepository.save(user);
        return true;
    }
    
    public boolean signUp(User user) {
        userRepository.save(user);
        return true;
    }
    
    //public User findUser(String name) {
        //User user = userRepository.findByName(name);
        //return user;
    //}
    
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }
    
    public boolean addSkill(String name, User user) {
        Skill existingSkill = skillRepository.findBySkill(name);

        if (existingSkill == null) {
            Skill skill = new Skill(name, user, new ArrayList<>());
            skillRepository.save(skill);
            return true;
        }

        return false;
    }

    public List<Skill> getSkills(){
        List<Skill> skills = skillRepository.findByUser(currentUser());
        return skills;
    }
    
    public boolean deleteSkill(Long id) {
        List<Endorsement> endorsements = endorsementRepository.findOneBySkillId(id);
        for (Endorsement endorsement: endorsements) {
            endorsementRepository.delete(endorsement);
        }
        skillRepository.deleteById(id);
        return true;
    }
    
    public boolean addOrDeleteEndorsement(User endorser, Long skillId) {
        Optional<Skill> potentialSkill = skillRepository.findById(skillId);

        if (potentialSkill.isPresent()) {
            Skill skill = potentialSkill.get();
            if (endorsementRepository.findBySkillAndEndorser(skill, endorser) == null) {
                Endorsement endorsement = new Endorsement(endorser, skill);
                endorsementRepository.save(endorsement);
            } else {
                Endorsement endorsement = endorsementRepository.findBySkillAndEndorser(skill, endorser);
                endorsementRepository.delete(endorsement);
            }
            return true;
        }

        return false;
    }

    public List<Endorsement> getEndorsementsBySkill(Long id){
        Optional<Skill> potentialSkill = skillRepository.findById(id);

        if (potentialSkill.isPresent()) {
            Skill skill = potentialSkill.get();
            List<Endorsement> endorsements = endorsementRepository.findBySkill(skill);
            return endorsements;
        }

        return new ArrayList<>();
    }
    
    public boolean deleteEndorsement(Endorsement endorsement) {
        endorsementRepository.delete(endorsement);
        return true;
    }

    public List<Skill> getSkillsById(Long id){
        Optional<User> potentialUser = userRepository.findById(id);

        if (potentialUser.isPresent()) {
            User user = potentialUser.get();
            List<Skill> skills = skillRepository.findByUser(user);
            return skills;
        }

        return new ArrayList<>();
    }
    
    public void addPhoto(MultipartFile file) throws IOException {
        if( (file.getContentType().equals("image/jpeg") || 
                file.getContentType().equals("image/jpg") || 
                file.getContentType().equals("image/png") ) && 
                file.getSize() < 10000000 ) {
            User user = currentUser();
            user.setPhotoContentType(file.getContentType());
            user.setPhoto(file.getBytes());
            userRepository.save(user);            
        }
    }
}
