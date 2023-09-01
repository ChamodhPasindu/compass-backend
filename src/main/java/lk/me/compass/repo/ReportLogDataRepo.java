package lk.me.compass.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lk.me.compass.entity.ReportLogData;

@Repository
public interface ReportLogDataRepo extends JpaRepository<ReportLogData,Integer>{
    
}
