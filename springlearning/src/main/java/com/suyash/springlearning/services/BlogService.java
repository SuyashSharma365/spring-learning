package com.suyash.springlearning.services;


import com.suyash.springlearning.entity.BlogEntity;
import com.suyash.springlearning.entity.UserEntity;
import com.suyash.springlearning.repository.BlogEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogEntryRepository blogEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(BlogEntity blogEntity , String userName){

        try{
            UserEntity user = userService.findByUserName(userName);

            if(user == null){
                throw new RuntimeException("User not found");
            }

            BlogEntity blog = blogEntryRepository.save(blogEntity);
            user.getBlogs().add(blog);
            userService.saveEntry(user);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public List<BlogEntity> getAll(){
        return blogEntryRepository.findAll();
    }

    public Optional<BlogEntity> findById(ObjectId id){
        return blogEntryRepository.findById(id);
    }
    public void  deleteById(ObjectId id){
        blogEntryRepository.deleteById(id);
    }

}
