package lk.me.compass.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lk.me.compass.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role,Integer>{
    

}
