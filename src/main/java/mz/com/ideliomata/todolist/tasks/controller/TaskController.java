package mz.com.ideliomata.todolist.tasks.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import mz.com.ideliomata.todolist.tasks.model.TaskModel;
import mz.com.ideliomata.todolist.tasks.repository.ITaskRepository;
import mz.com.ideliomata.todolist.utils.Utils;

@RestController
@RequestMapping("/tasks")
public class TaskController {
      
    @Autowired
    private ITaskRepository iTaskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){

        //Define o id do Usuario
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        //Validacao dia e  hora de cadastro. Current time after Time.Now
        var currentDate = LocalDateTime.now();

        if ( currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) { 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("A data de início / data de término deve ser maior que a data atual");
        }
        
        
        if ( taskModel.getStartAt().isAfter(taskModel.getEndAt())) { 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("A data de início  deve ser menor que a data do término");
        }

        var task = this.iTaskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }


    
    @GetMapping("/")
    public List <TaskModel>  list (HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.iTaskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){
        var task = this.iTaskRepository.findById(id).orElse( null);

        if (task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Tarefa não encontrada");
        }

        var idUser = request.getAttribute("idUser");
        
        if (!task.getIdUser().equals(idUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Usuario não tem permissão para alterar essa tarefa");
        }

        Utils.copyNullProperties(taskModel, task);

        var taskUpdated = this.iTaskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);
    }
}