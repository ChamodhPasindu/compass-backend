package lk.me.compass.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import lk.me.compass.entity.Quarter;

@Repository
public interface QuarterRepo extends JpaRepository<Quarter,Integer>{
    
    final String IS_EXIST_QUARTER = "select * from quarter where year=?1 and quarter_number=?2";

    @Query(value = IS_EXIST_QUARTER, nativeQuery = true)
    Optional<Quarter> findQuarterByYearAndQuarter(Integer year,Integer quarter);

    final String FIND_ALL_QUARTER_YEARS = "select distinct year from quarter";

    @Query(value = FIND_ALL_QUARTER_YEARS, nativeQuery = true)
    List<Object[]> findAllQuarterYears();


}
