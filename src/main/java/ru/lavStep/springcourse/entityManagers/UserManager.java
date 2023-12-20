package ru.lavStep.springcourse.entityManagers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
//import ru.lavStep.springcourse.EntiManagers.UserMapper;
import ru.lavStep.springcourse.entites.Role;
import ru.lavStep.springcourse.entites.User;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.lavStep.springcourse.entityMapper.UserMapper;

import java.util.List;

@Component
public class UserManager {
    public  final JdbcTemplate jdbcTemplate;
    private List<User> users;

    private final String INSERT_USER_SQL_REQ = "INSERT INTO project.users VALUES(?, ?, ?, ?)";
    private final String SELECT_ALL_USERS_SQL_REQ = "SELECT * FROM project.users";
    private final String SELECT_USER_SQL_REQ = "SELECT * FROM project.users WHERE project.users.id=";
    private final String UPDATE_USER_SQL_REQ = "UPDATE project.users SET first_name = ?, last_name = ? WHERE project.users.id=?";
    private final String DELETE_USER_SQL_REQ = "DELETE FROM project.users WHERE project.users.id=";

    private final String DELETE_PERFOMER_FROM_TASK_SQL_REQ = "UPDATE project.tasks SET performer=null WHERE project.tasks.performer=";

    @Autowired
    public UserManager(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createUser(User user){
        Long maxId = getMaxIdUser();
        Long id;
        if (maxId != null){
            id = getMaxIdUser() + 1;
        } else {id = Long.valueOf(0);}
        //Убрать после изменения бд Role
        user.setRole(Role.superUser);
        user.setId(id);
        jdbcTemplate.update(INSERT_USER_SQL_REQ, user.getId(), user.getFirstName(),
                user.getLastName(), user.getRole().name());
        return id;
    }

    public List<User> getAllUsers(){
        return jdbcTemplate.query(SELECT_ALL_USERS_SQL_REQ, new UserMapper());
    }

    public User getUser(Long id){
        User user = null;
        String req = SELECT_USER_SQL_REQ + id;
        Object a= jdbcTemplate.queryForObject(req, new UserMapper());
        if (a !=null) {user = (User) jdbcTemplate.queryForObject(req, new UserMapper());}

        return user;
    }

    public void updateUser(long id, User user){
        jdbcTemplate.update(UPDATE_USER_SQL_REQ, user.getFirstName(), user.getLastName(), id);
    }

    public Long getMaxIdUser(){
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM project.users", Long.class);
    }

    public void deleteUser(Long id){
        jdbcTemplate.update(DELETE_USER_SQL_REQ + id);
        jdbcTemplate.update(DELETE_PERFOMER_FROM_TASK_SQL_REQ + id);
    }

}
