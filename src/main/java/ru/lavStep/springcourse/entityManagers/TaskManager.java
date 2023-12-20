package ru.lavStep.springcourse.entityManagers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.lavStep.springcourse.entites.Status;
import ru.lavStep.springcourse.entites.Task;
import ru.lavStep.springcourse.entityMapper.TaskMapper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Component
public class TaskManager {

    public  final JdbcTemplate jdbcTemplate;

    private final String INSERT_TASK_SQL_REQ = "INSERT INTO project.tasks VALUES(?, ?, ?, ?, ?, ?, ?)";
    private final String SELECT_TASKS_BY_USER_SQL_REQ = "SELECT * FROM project.tasks WHERE project.tasks.performer=";
    private final String SELECT_ALL_TASKS_SQL_REQ = "SELECT * FROM project.tasks";
    private final String SELECT_TASK_SQL_REQ = "SELECT * FROM project.tasks WHERE project.tasks.id=";
    private final String UPDATE_TASK_SQL_REQ = "UPDATE project.tasks SET performer = ?, name = ?, description = ?, deadline = ?, task_status = ? WHERE project.tasks.id=?";
    private final String DELETE_TASK_SQL_REQ = "DELETE FROM project.tasks WHERE project.tasks.id=";


    public TaskManager(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createTask(Task task){
        Long maxId = getMaxIdTasks();
        Long id;
        if (maxId != null){
             id = getMaxIdTasks() + 1;
        } else {id = Long.valueOf(0);}

        task.setId(id);

        LocalDate dateCreated  = LocalDate.now();
        task.setDateCreated(dateCreated);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale( Locale.getDefault() );
        LocalDate deadline = LocalDate.parse(task.getDeadline(), formatter);


        task.setStatus(Status.InWork);

        jdbcTemplate.update(INSERT_TASK_SQL_REQ,
                task.getId(), task.getPerformer(), task.getName(), task.getDescription(), task.getDateCreated(),
                deadline, task.getStatus().name());
        return id;
    }

    public List<Task> getAllUserTasks(Long userId){
        String req = SELECT_TASKS_BY_USER_SQL_REQ + userId;
        return jdbcTemplate.query(req, new TaskMapper());
    }

    public List<Task> getAllTasks(){
        return jdbcTemplate.query(SELECT_ALL_TASKS_SQL_REQ, new TaskMapper());
    }

    public Task getTask(Long taskId){
        String req = SELECT_TASK_SQL_REQ + taskId;
        return (Task) jdbcTemplate.queryForObject(req, new TaskMapper());
    }

    public void updateTask(long id, Task task){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale( Locale.getDefault() );
        LocalDate deadline = LocalDate.parse(task.getDeadline(), formatter);

        task.setStatus(Status.InWork);

        jdbcTemplate.update(UPDATE_TASK_SQL_REQ, task.getPerformer(), task.getName(), task.getDescription(),
                deadline, task.getStatus().name(), id);
    }

    public Long getMaxIdTasks(){return jdbcTemplate.queryForObject("SELECT MAX(id) FROM project.tasks", Long.class);}

    public void deleteTask(Long id){
        jdbcTemplate.update(DELETE_TASK_SQL_REQ + id);
    }

}
