/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.entitles.Connection;
import projekti.entitles.User;
import projekti.repositories.ConnectionRepository;
import projekti.repositories.UserRepository;

/**
 *
 * @author saasini
 */
@Service
public class ConnectionService {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ConnectionRepository connectionRepository;
    
    public boolean sendConnectionRequest(User sender, User receiver) {
        Connection request = new Connection(sender, receiver, false);
        connectionRepository.save(request);
        return true;
    }
    
    public void acceptRequest(Long id) {
        Optional<Connection> potentialRequest = connectionRepository.findById(id);
        
        if (potentialRequest.isPresent()) {
            Connection request = potentialRequest.get();           
            request.setAccepted(true);
            connectionRepository.save(request); 
        }
    }
    
    public boolean cancelRequest(Connection request) {
        connectionRepository.delete(request);
        return true;
    }
    
        
    public void deleteConnection(Long id) {
        Optional<Connection> potentialConnection = connectionRepository.findById(id);
        
        if (potentialConnection.isPresent()){
            Connection connection = potentialConnection.get();
            connectionRepository.delete(connection);
        }  
    }
    
    public void sendRequest(Long id) {
        Optional<User> potentialUser = userService.getById(id);
        
        if (potentialUser.isPresent()){
            User user = potentialUser.get();
            
            Connection connection = new Connection(userService.currentUser(), user, false);
            connectionRepository.save(connection);
        }
        
    }
    
    public List<Connection> getConnectionRequests(User user) {
        List<Connection> requests = new ArrayList<>();
        
        for (Connection connection : connectionRepository.findByReceiver(user)) {
            if (!connection.isAccepted()){
                requests.add(connection);
            }
        }
        return requests;
    }
    
    public List<User> getSentRequests() {
        User user = userService.currentUser();
        List<User> requests = new ArrayList<>();
        
        for (Connection connection : connectionRepository.findBySender(user)) {
                requests.add(connection.getReceiver());
        }
        
        for (Connection connection : connectionRepository.findByReceiver(user)) {
                requests.add(connection.getSender());
        }
        return requests;
    }
    
    
    public List<Connection> getConnections(User user) {
        List<Connection> connections = new ArrayList<>();
        for (Connection connection : connectionRepository.findByReceiver(user)) {
            if (connection.isAccepted()) {
                connections.add(connection);
            }
        }
        for (Connection connection : connectionRepository.findBySender(user)) {
            if (connection.isAccepted() && !connections.contains(connection)) {
                connections.add(connection);
            }
        }
        return connections;
    }
    
        public List<Connection> getPendingRequests() {
        User user = userService.currentUser();
        
        List<Connection> connections = new ArrayList<>();

        for (Connection connection : connectionRepository.findBySender(user)) {
            if (!connection.isAccepted()) {
                connections.add(connection);
            }
        }
        return connections;
    }
    
    public List<User> findNewConnections(String string) {
        User user = userService.currentUser();
        List<User> connectionsFound = new ArrayList<>();
        
        for (User u : userService.getAllUsers() ){
            if ((!user.getConnections().contains(u)) && 
                    u.getFullname().toLowerCase().contains(string.toLowerCase()) && 
                    (!u.equals(user))) {
                connectionsFound.add(u);
            }
        }        
        return connectionsFound;
    }
}
