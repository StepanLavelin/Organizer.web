package ru.lavStep.springcourse.entityMapper;

import org.springframework.jdbc.core.RowMapper;
import ru.lavStep.springcourse.entites.Role;
import ru.lavStep.springcourse.entites.Status;
import ru.lavStep.springcourse.entites.Task;
import ru.lavStep.springcourse.entites.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper implements RowMapper{

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Task task = new Task();

        task.setId(resultSet.getLong("id"));
        task.setPerformer(resultSet.getLong("performer"));
        task.setName(resultSet.getString("name"));
        task.setDescription(resultSet.getString("description"));
        task.setDateCreated(resultSet.getDate("date_created").toLocalDate());
        task.setDeadline(resultSet.getString("deadline"));
        task.setStatus(Status.valueOf(resultSet.getString("task_status")));

        return task;
    }
}
