package ca.sheridancollege.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.domain.MenuItem;

public interface MenuItemRepository extends JpaRepository <MenuItem, Long> {
	public MenuItem findByName(String name);
}
