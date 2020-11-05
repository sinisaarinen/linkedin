/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private UserRepository userRepository;
    
    @Autowired
    private ConnectionRepository connectionRepository;
    
    public boolean sendConnectionRequest(User sender, User receiver) {
        Connection request = new Connection(sender, receiver, false);
        connectionRepository.save(request);
        return true;
    }
    
    public boolean acceptRequest(Connection request) {
        request.setAccepted(true);
        connectionRepository.save(request);
        return true;
    }
    
    public boolean cancelRequest(Connection request) {
        connectionRepository.delete(request);
        return true;
    }
    
    public boolean deleteConnection(Connection connection) {
        connectionRepository.delete(connection);
        return true;
    }
    
    public List<Connection> getConnectionRequests(User user) {
        List<Connection> requests = new ArrayList<>();
        for (Connection connection: connectionRepository.findByReceiver(user)) {
            if (!connection.isAccepted()){
                requests.add(connection);
            }
        }
        return requests;
    }
    
    public List<Connection> getConnections(User user) {
        List<Connection> connections = new ArrayList<>();
        for (Connection connection: connectionRepository.findByReceiver(user)) {
            if (!connection.isAccepted()){
                connections.add(connection);
            }
        }
        for (Connection connection: connectionRepository.findBySender(user)) {
            if (!connection.isAccepted() && !connections.contains(connection)){
                connections.add(connection);
            }
        }
        return connections;
    }  
}
