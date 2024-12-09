package org.todolist.todolistapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.todolist.todolistapi.model.Task;
import org.todolist.todolistapi.model.User;
import org.todolist.todolistapi.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    //Crear nueva task
    public Task createTask(String title,  User user){
        Task task = new Task(title, false, user);
        return taskRepository.save(task);
    }

    //Listar todas las task de un usuario
    public List<Task> getTaskByUser(User user){
        return taskRepository.findByUser(user);
    }

    //Filtrar tasks por estado
    public List<Task> getTasksByUserAndStatus(User user, boolean completed){
        return taskRepository.findByUserAndCompleted(user, completed);
    }

    //Actualizar el estado de la task
    public Task updateTaskStatus(Long taskId, boolean completed){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task no encontrada"));
        task.setCompleted(completed);
        return taskRepository.save(task);
    }

    //Eliminar una task
    public void deleteTask(Long taskId){
        taskRepository.deleteById(taskId);
    }
}
