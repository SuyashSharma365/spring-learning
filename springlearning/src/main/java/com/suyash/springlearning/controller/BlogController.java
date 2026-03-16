package com.suyash.springlearning.controller;


import com.suyash.springlearning.entity.BlogEntity;
import com.suyash.springlearning.entity.UserEntity;
import com.suyash.springlearning.services.BlogService;
import com.suyash.springlearning.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<BlogEntity> createBlog(@RequestBody BlogEntity blogEntity){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.findByUserName(authentication.getName());

        try{
            blogService.saveEntry(blogEntity , user.getUserName());
            return new ResponseEntity<>(blogEntity , HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping
    public ResponseEntity<?> getBlog(@PathVariable String userName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.findByUserName(authentication.getName());
//        UserEntity user = userService.findByUserName(userName);
        List<BlogEntity> all = user.getBlogs();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<?> deleteBlog(@PathVariable ObjectId blogId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.findByUserName(authentication.getName());

        if(user != null){

            boolean found = user.getBlogs().removeIf(blog -> blog.getId().equals(blogId));

            if(!found){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            blogService.deleteById(blogId);
            userService.saveOldEntry(user);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{blogId}")
    public ResponseEntity<?> updateBlog(@PathVariable ObjectId blogId, @RequestBody BlogEntity newBlog){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.findByUserName(authentication.getName());

        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        boolean found = user.getBlogs().stream().anyMatch(blog -> blog.getId().equals(blogId));
        Optional<BlogEntity> blogOptional = blogService.findById(blogId);

        if(blogOptional.isPresent() && found){
            BlogEntity oldBlog = blogOptional.get();

            oldBlog.setContent(
                    newBlog.getContent() != null && !newBlog.getContent().equals("")
                            ? newBlog.getContent()
                            : oldBlog.getContent()
            );

            oldBlog.setTitle(
                    newBlog.getTitle() != null
                            ? newBlog.getTitle()
                            : oldBlog.getTitle()
            );
            blogService.saveEntry(oldBlog , user.getUserName());
            return new ResponseEntity<>(HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
