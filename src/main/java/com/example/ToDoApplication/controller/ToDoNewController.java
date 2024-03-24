package com.example.ToDoApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.ToDoApplication.service.ToDoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.example.ToDoApplication.model.ToDo;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ToDoNewController {

    @Autowired
    private ToDoService todoService;

    @GetMapping("/new-todo-item")
    public String showCreateForm(ToDo todo) {
        return "new-todo-item";
    }

    @PostMapping("/todo")
    public String createTodoItem(@Valid ToDo todo, BindingResult result, Model model) {

        ToDo todo_item = new ToDo();
        todo_item.setTitle(todo.getTitle());
        todo_item.setStatus(todo.getStatus());

        todoService.saveOrUpdateToDoItem(todo);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodoItem(@PathVariable("id") Long id, Model model) {
        //ToDo todo = todoService.getToDoItemById(id);
                //.orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));

        todoService.deleteToDoItem(id);
        return "redirect:/";
    }

    @GetMapping("/updateToDoStatus/{id}")
	public String updateToDoStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		if (todoService.updateStatus(id)) {
			redirectAttributes.addFlashAttribute("message", "Update Success");
			return "redirect:/";
		}
		
		redirectAttributes.addFlashAttribute("message", "Update Failure");
		return "redirect:/";
	}

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        ToDo toDo = todoService.getToDoItemById(id);
                //.orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));

        model.addAttribute("todo", toDo);
        return "edit-todo-item";
    }

    @PostMapping("/todo/{id}")
    public String updateTodoItem(@PathVariable("id") Long id, @Valid ToDo updatedTodo, BindingResult result, Model model) {
        // Get the existing ToDo item by ID
        ToDo existingTodo = todoService.getToDoItemById(id);

        // Update the properties of the existing ToDo item with the values from the updated ToDo object
        existingTodo.setTitle(updatedTodo.getTitle());
        existingTodo.setDate(updatedTodo.getDate());
        existingTodo.setStatus(updatedTodo.getStatus());

        // Save the updated ToDo item
        todoService.saveOrUpdateToDoItem(existingTodo);

        // Redirect to the home page
        return "redirect:/";
    }

}