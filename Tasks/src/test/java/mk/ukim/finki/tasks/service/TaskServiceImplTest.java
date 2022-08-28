package mk.ukim.finki.tasks.service;

import mk.ukim.finki.tasks.domain.models.Task;
import mk.ukim.finki.tasks.domain.models.TaskUser;
import mk.ukim.finki.tasks.domain.models.TaskUserId;
import mk.ukim.finki.tasks.domain.repository.TaskUserRepository;
import mk.ukim.finki.tasks.domain.valueobjects.UserId;
import mk.ukim.finki.tasks.service.form.TaskForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootTest
public class TaskServiceImplTest {

    @Autowired
    private TaskService taskService;

    @Autowired

    //TODO change to service
    private TaskUserRepository taskUserRepository;

    @Test
    public void createTask(){

        TaskUser taskUser = new TaskUser();
        taskUserRepository.save(taskUser);
        TaskForm taskForm = new TaskForm();
        taskForm.setTitle("Task 1");
        taskForm.setDescription("Task 1 desc");
        taskForm.setDependsOn(new ArrayList<>());
        taskForm.setUserId(taskUser.getId().getId());
        taskForm.setStartTime(LocalDateTime.now());
        taskForm.setEndTime(LocalDateTime.of(2022,9,9,22,0));

        this.taskService.create(taskForm);
        Assertions.assertEquals(1,taskService.findAll().size());
    }

    @Test
    public void updateTask(){
        Task task = this.taskService.findAll().get(0);
        TaskForm taskForm = new TaskForm();
        taskForm.setTitle("Task 1");
        taskForm.setDescription("Task 1 updated description");
        taskForm.setDependsOn(new ArrayList<>());
        taskForm.setUserId(this.taskUserRepository.findAll().get(0).getId().getId());
        taskForm.setStartTime(LocalDateTime.now());
        taskForm.setEndTime(LocalDateTime.of(2022,9,9,22,0));

        this.taskService.update(task.getId().getId(),taskForm);

        Assertions.assertEquals("Task 1 updated description",taskService.findAll().get(0).getDescription());
    }

    @Test
    public void deleteTask(){
        Task task = this.taskService.findAll().get(0);
        this.taskService.delete(task.getId().getId());
        Assertions.assertEquals(0,taskService.findAll().size());
    }

}
