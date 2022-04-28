package ca.sheridancollege.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.domain.MenuItem;
import ca.sheridancollege.services.MenuItemService;

@RestController
@RequestMapping("/api/menuitems")
@CrossOrigin(origins="http://localhost:8080")
public class MenuItemController {

	@Autowired
	MenuItemService menuItemService;

	// get all menu items
	@GetMapping(value = {"", "/"})
	public List<MenuItem> getMenuItemCollection(){
		return menuItemService.findAll();
	}
	
	// save a menu item
	@PostMapping(value = {"", "/"})
	public MenuItem save(@RequestBody MenuItem menuItem){
		return menuItemService.save(menuItem);
	}
	
	@DeleteMapping(value="/{id}")
	public String deleteMenuItemById(@PathVariable Long id)
	{
		menuItemService.deleteById(id);
		return "Deleted id: " + id;
	}
	
	@PostMapping(value="/printreciept")
	public void printReciept(@RequestBody String text) {
		File f = new File("reciept.txt");
		
		try {
			FileWriter fw = new FileWriter(f, false);
			fw.write(text);
			fw.close();
		} catch (IOException e) {
			System.out.println("unable to print to reciept");
		}
	}
}
