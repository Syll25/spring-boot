package com.example.spring_boot;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{

  void save(User user);

  Page<User> findAll(Pagination pagination);
}
