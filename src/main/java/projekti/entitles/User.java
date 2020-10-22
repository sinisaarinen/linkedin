/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.entitles;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author saasini
 */
@Entity
@Table(name = "Profile")
@Data @AllArgsConstructor @NoArgsConstructor
public class User extends AbstractPersistable<Long> {
    
    @NotEmpty
    @Size(min = 4, max = 15)
    private String username;
    
    @NotEmpty
    @Size(min = 4, max = 150)
    private String password;
    
    @NotEmpty
    @Size(min = 4, max = 30)
    private String fullname;
    
    @NotEmpty
    @Size(min = 4, max = 15)
    private String profilename;
    
    @OneToMany
    private List<Post> posts;
    
    @ElementCollection(targetClass = String.class)
    private List<Connection> connections;
    
    @OneToMany
    private List<ConnectionRequest> connectionRequests;
    
    @OneToMany
    private List<Like> likes;
    
    @OneToMany
    private List<Endorsement> endorsements;
    
    @OneToMany
    private List<Skill> skills;
}
