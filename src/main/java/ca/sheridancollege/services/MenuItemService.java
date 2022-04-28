package ca.sheridancollege.services;

import java.util.List;

import ca.sheridancollege.domain.MenuItem;

public interface MenuItemService {
	public List<MenuItem> findAll();
	public MenuItem findById(Long id);
	public MenuItem save(MenuItem menuItem);
	public void deleteById(Long id);
}
