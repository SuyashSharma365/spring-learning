package com.suyash.springlearning.controller;


import com.suyash.springlearning.entity.BlogEntity;
import com.suyash.springlearning.entity.UserEntity;
import com.suyash.springlearning.services.BlogService;
import com.suyash.springlearning.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/{userName}")
    public ResponseEntity<BlogEntity> createBlog(@RequestBody BlogEntity blogEntity , @PathVariable String userName){

        try{
            blogService.saveEntry(blogEntity , userName);
            return new ResponseEntity<>(blogEntity , HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getBlog(@PathVariable String userName){
        UserEntity user = userService.findByUserName(userName);
        List<BlogEntity> all = user.getBlogs();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userName}/{blogId}")
    public ResponseEntity<?> deleteBlog(@PathVariable ObjectId blogId , @PathVariable String userName){
        UserEntity user = userService.findByUserName(userName);

        if(user != null){
            blogService.deleteById(blogId);
            user.getBlogs().removeIf(x -> x.getId().equals(blogId));
            userService.saveEntry(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{userName}/{blogId}")
    public ResponseEntity<?> updateBlog(@PathVariable ObjectId blogId, @PathVariable String userName , @RequestBody BlogEntity newBlog){
        Optional<BlogEntity> blogOptional = blogService.findById(blogId);
        if(blogOptional.isPresent()){
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
            blogService.saveEntry(oldBlog , userName);
            return new ResponseEntity<>(HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
