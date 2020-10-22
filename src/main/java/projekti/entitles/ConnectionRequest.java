/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.entitles;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author saasini
 */
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class ConnectionRequest extends AbstractPersistable<Long> {
    
    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    private Date date;
}
