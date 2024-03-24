package com.example.ToDoApplication.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ToDoApplication.model.ToDo;
import com.example.ToDoApplication.repository.ToDoRepository;

@Service
public class ToDoService {
	
	@Autowired
	ToDoRepository repository;

	
	public List<ToDo> getAllToDoItems() {
		ArrayList<ToDo> todoList = new ArrayList<>();
		repository.findAll().forEach(todo -> todoList.add(todo));
		return todoList;
	}
	
	public ToDo getToDoItemById(Long id) {
		return repository.findById(id).get();
	}
	
	public boolean updateStatus(Long id) {
		ToDo todo = getToDoItemById(id);
		todo.setStatus("Completed");
		
		return saveOrUpdateToDoItem(todo);
	}
	
	public boolean saveOrUpdateToDoItem(ToDo todo) {
			ToDo updatedObj = repository.save(todo);
			
			if (getToDoItemById(updatedObj.getId()) != null) {
				return true;
			}
			return false;
	}
	
	public boolean deleteToDoItem(Long id) {
		repository.deleteById(id);
		
		if (repository.findById(id).isEmpty()) {
			return true;
		}
		
		return false;
	}
	
}