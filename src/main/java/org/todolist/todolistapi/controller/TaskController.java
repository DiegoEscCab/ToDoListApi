package org.todolist.todolistapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.todolist.todolistapi.model.Task;
import org.todolist.todolistapi.model.User;
import org.todolist.todolistapi.service.TaskService;
import org.todolist.todolistapi.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    //Create new task
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task, @RequestParam String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Task newTask = taskService.createTask(task.getTitle(), user);
        return ResponseEntity.ok(newTask);
    }

    //List all tasks from an user
    @GetMapping
    public ResponseEntity<?> getTaskByUser(@RequestParam String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        List<Task> tasks = taskService.getTaskByUser(user);
        return ResponseEntity.ok(tasks);
    }

    //Filtrar tasks por estado
    @GetMapping("/status")
    public  ResponseEntity<?> getTasksByStatus(@RequestParam String username, @RequestParam boolean completed){
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        List<Task> tasks = taskService.getTasksByUserAndStatus(user, completed);
        return ResponseEntity.ok(tasks);
    }

    //Actualizar el estado de una tarea
    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Long taskId, @RequestParam boolean completed){
        try {
            Task updatedTask = taskService.updateTaskStatus(taskId,completed);
            return ResponseEntity.ok(updatedTask);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Eliminar tarea
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deketeTask(@PathVariable Long taskId){
        try {
            taskService.deleteTask(taskId);
            return ResponseEntity.ok("Tarea eliminada exitosamente");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
