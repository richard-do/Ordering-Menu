package ca.sheridancollege.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "user_details")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long userId;
	@NonNull
	private String username;
	@NonNull
	private String encryptedPassword;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Role> roleList = new ArrayList<Role>();
	
	
}
