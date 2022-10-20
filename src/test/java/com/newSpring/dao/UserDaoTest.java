package com.newSpring.dao;

import com.newSpring.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    UserDao userDao;

    @BeforeEach
    void setUp(){

        this.userDao = context.getBean("awsUserDao", UserDao.class);
        System.out.println("before each");
    }

    @Test
    void addAndGet() throws SQLException {
        User user1 = new User("1", "AAA", "123");


        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        User user = userDao.findById(user1.getId());

        assertEquals(user1.getName(), user.getName());
        assertEquals(user1.getPassword(), user.getPassword());
    }

    @Test
    void count() throws SQLException{

        User user1 = new User("1", "AAA", "123");
        User user2 = new User("2", "BBB", "456");
        User user3 = new User("3", "CCC", "789");

        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        userDao.add(user2);
        assertEquals(2, userDao.getCount());
        userDao.add(user3);
        assertEquals(3, userDao.getCount());
    }

    @Test
    void findById(){
        assertThrows(EmptyResultDataAccessException.class, () ->{
            userDao.findById("30");
        });
    }
}