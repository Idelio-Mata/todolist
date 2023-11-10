package mz.com.ideliomata.todolist.tasks.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import mz.com.ideliomata.todolist.tasks.model.TaskModel;
import java.util.List;


public interface ITaskRepository extends JpaRepository <TaskModel, UUID>{

    List<TaskModel> findByIdUser(UUID idUser);
    TaskModel findByIdAndIdUser( UUID id, UUID idUser);

    
    
}
