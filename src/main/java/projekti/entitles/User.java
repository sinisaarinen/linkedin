/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.entitles;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author saasini
 */
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name="Users")
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
    
    @Column(name = "photo", length = 500000)
    private byte[] photo;
    
    private String photoContentType;
    
    @OneToMany
    private List<Post> posts;
    
    @OneToMany
    private List<Connection> connections;
    
    @OneToMany
    private List<Connection> connectionRequests;
    
    @OneToMany
    private List<Like> likes;
    
    @OneToMany
    private List<Endorsement> endorsements;
    
    @OneToMany
    private List<Skill> skills;
}
