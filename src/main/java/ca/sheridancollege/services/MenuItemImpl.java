package ca.sheridancollege.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sheridancollege.domain.MenuItem;
import ca.sheridancollege.repositories.MenuItemRepository;

@Service
public class MenuItemImpl implements MenuItemService {

	@Autowired
	MenuItemRepository menuItemRepository;
	
	@Override
	public List<MenuItem> findAll(){
		return menuItemRepository.findAll();
	}
	
	@Override
	public MenuItem findById(Long id) {
		return menuItemRepository.findById(id).get();
	}
	
	@Override
	public MenuItem save(MenuItem menuItem) {
		return menuItemRepository.save(menuItem);
	}
	
	@Override
	public void deleteById(Long id) {
		menuItemRepository.deleteById(id);
	}
	
}
