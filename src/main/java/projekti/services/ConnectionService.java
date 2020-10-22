/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.services;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.entitles.Connection;
import projekti.entitles.ConnectionRequest;
import projekti.entitles.User;
import projekti.repositories.ConnectionRepository;
import projekti.repositories.ConnectionRequestRepository;
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
    
    @Autowired
    private ConnectionRequestRepository connectionRequestRepository;
    
    public boolean sendConnectionRequest(User sender, User receiver) {
        Date date = new Date(System.currentTimeMillis());
        ConnectionRequest request = new ConnectionRequest(sender, receiver, date);
        connectionRequestRepository.save(request);
        return true;
    }
    
    public boolean acceptRequest(ConnectionRequest request) {
        Connection connection = new Connection(request.getSender(), request.getReceiver());
        connectionRepository.save(connection);
        return true;
    }
    
    public boolean cancelRequest(ConnectionRequest request) {
        connectionRequestRepository.delete(request);
        return true;
    }
    
    public boolean deleteConnection(Connection connection) {
        connectionRepository.delete(connection);
        return true;
    }
}
