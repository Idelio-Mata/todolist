package mz.com.ideliomata.todolist.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import mz.com.ideliomata.todolist.user.model.UserModel;

public interface InterfaceUserRepository extends JpaRepository< UserModel, UUID> {

    UserModel findByUsername(String username);
     
}
