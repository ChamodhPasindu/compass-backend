package lk.me.compass.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lk.me.compass.entity.LogData;

@Repository
public interface LogDataRepo extends JpaRepository<LogData,Integer> {
    
}
