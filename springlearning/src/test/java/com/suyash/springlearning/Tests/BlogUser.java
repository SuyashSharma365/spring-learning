package com.suyash.springlearning.Tests;

import com.suyash.springlearning.entity.BlogEntity;
import com.suyash.springlearning.entity.UserEntity;
import com.suyash.springlearning.repository.UserEntryRepository;
import com.suyash.springlearning.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BlogUser {

    @InjectMocks
    UserService userService;

    @Mock
    UserEntryRepository userEntryRepository;

    @Test
    public void testFindUserWithBlogs(){
        BlogEntity blog = new BlogEntity();
        blog.setTitle("Mockito Blog");

        UserEntity user = new UserEntity();
        user.setUserName("suyash");
        user.setBlogs(List.of(blog));


        when(userEntryRepository.findByUserName("suyash")).thenReturn(user);


        UserEntity result = userService.findByUserName("suyash");


        assertNotNull(result);
        assertEquals("suyash", result.getUserName());
        assertEquals(1, result.getBlogs().size());
        assertEquals("Mockito Blog", result.getBlogs().get(0).getTitle());
    }
}
