/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.entitles.Connection;
import projekti.entitles.Endorsement;
import projekti.entitles.Skill;
import projekti.entitles.User;
import projekti.repositories.UserRepository;
import projekti.services.ConnectionService;
import projekti.services.UserService;

/**
 *
 * @author saasini
 */
@Controller
public class ConnectionController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ConnectionService connectionService;
    
    @RequestMapping("/connections")
    public String connections(Model model) {      
        User user = userService.currentUser();
        List<Connection> connections = connectionService.getConnections(userService.currentUser());
        List<Connection> connectionRequests = connectionService.getConnectionRequests(userService.currentUser());
        List<User> sentRequests = connectionService.getSentRequests();
        List<User> users = userService.getAllUsers();
        
        List<User> usersWithoutCurrent = new ArrayList<>();
        
        for (User u : users) {
            if (u.getId() != user.getId() && !(sentRequests.contains(u))) {
                usersWithoutCurrent.add(u);
            }
        }
        
        model.addAttribute("connections", connections);
        model.addAttribute("connectionRequests", connectionRequests);
        model.addAttribute("allUsers", usersWithoutCurrent);
        model.addAttribute("user", user);

        return "connections";
    }
    
    @PostMapping("/connections/search")
    public String searchForConnections(@RequestParam String string, Model model) {
        model.addAttribute("connections", connectionService.getConnections(userService.currentUser()));
        model.addAttribute("connectionrequests", connectionService.getConnectionRequests(userService.currentUser()));
        model.addAttribute("allUsers", connectionService.findNewConnections(string));
        return "connections";
    }
    
    @PostMapping("/connections/accept/{id}")
    public String acceptConnectionRequest(@PathVariable Long id, Model model) {
        connectionService.acceptRequest(id);
        return "redirect:/connections";
    }
    
        @PostMapping("/connections/ignore/{id}")
    public String ignoreConnectionRequest(@PathVariable Long id, Model model) {
        connectionService.deleteConnection(id);
        return "redirect:/connections";
    }
    
    @PostMapping("/connections/remove/{id}")
    public String removeConnection(@PathVariable Long id, Model model) {
        connectionService.deleteConnection(id);
        return "redirect:/connections";
    }
    @PostMapping("/connections/send/{id}")
    public String sendRequest(@PathVariable Long id) {
        connectionService.sendRequest(id);
        return "redirect:/connections";
    }
    
}