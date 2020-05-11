package com.cesske.mps.repository;

import com.cesske.mps.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 
 */
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {

    User findByRidEquals(Long rid);

    User findByMobileAndStatusEquals(String mobile, int status);

}