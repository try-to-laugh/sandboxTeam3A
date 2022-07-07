package com.exadel.sandboxteam3a.dao;

import com.exadel.sandboxteam3a.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

}
