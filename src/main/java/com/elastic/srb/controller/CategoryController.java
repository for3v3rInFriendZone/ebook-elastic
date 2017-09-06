package com.elastic.srb.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.elastic.srb.model.Category;
import com.elastic.srb.service.CategoryService;

@RestController
@RequestMapping(value="/category")
public class CategoryController {

	@Autowired
	CategoryService cateSer;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getCategories() {

		List<Category> categories = (List<Category>) cateSer.findAll();
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Category> getCategory(@PathVariable Long id) {

		Category category = cateSer.findOne(id);
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Category> editCategory(@PathVariable("id") Long id, @RequestBody Category category) {

		Category editedCategory = cateSer.findOne(id);
		editedCategory.setName(category.getName());
		
		cateSer.save(editedCategory);
		return new ResponseEntity<Category>(editedCategory, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Category> saveCategory(@RequestBody Category category) {

		Category newCategory = cateSer.save(category);
		return new ResponseEntity<Category>(newCategory, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Category> deleteCategory(@PathVariable("id") Long id) {

		cateSer.delete(id);
		return new ResponseEntity<Category>(HttpStatus.OK);
	}
}
