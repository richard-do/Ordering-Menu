package ca.sheridancollege.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.micrometer.core.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MenuItem {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	private String name;
	private String description;
	private Float price;
	private String imageSource;
	
	public MenuItem (String name, Float price, String description){
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	public MenuItem (String name, Float price, String description, String imageSource) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.imageSource = imageSource;
	}
}
