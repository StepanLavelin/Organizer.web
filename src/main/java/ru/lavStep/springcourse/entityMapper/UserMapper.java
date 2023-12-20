package ru.lavStep.springcourse.entityMapper;

import ru.lavStep.springcourse.entites.Role;
import ru.lavStep.springcourse.entites.User;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper{

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException{
        User user = new User();

        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setRole(Role.valueOf(resultSet.getString("role_user")));

        return user;
    }
}
