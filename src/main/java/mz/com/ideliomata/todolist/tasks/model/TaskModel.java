package mz.com.ideliomata.todolist.tasks.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.print.DocFlavor.STRING;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {
    // ID; Usuário_id; Descrição; Título; Data de Iníncio; Data de término; Prioridade

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private UUID idUser;
     
    private String description;
    
    @Column(length = 50)
    private String title;
     
    private String priority;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    
    @CreationTimestamp
    private LocalDateTime createdAt;

    public void SetTitle(String title) throws Exception{ 
        if (title.length()> 50) {
            throw new Exception("O campo title deve conter no máximo 50 caracteres");
        }
        this.title= title;
    }
    
}
