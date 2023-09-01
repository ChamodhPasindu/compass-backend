package lk.me.compass.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import lk.me.compass.entity.QuarterData;

@Repository
public interface QuarterDataRepo extends JpaRepository<QuarterData,Integer>{
    
    final String FIND_ARTIST_REPORT_DATA_BY_ARTIST_ID = "SELECT q.quarter_number, q.year, qd.artist, qd.asset_title, qd.custom_id, qd.platform, qd.revenue, qd.revenue_share, qr.id, qd.isrc FROM quarter_data qd INNER JOIN quarter_report qr ON qd.quarter_report_id = qr.id INNER JOIN quarter q ON q.id = qr.quarter_id WHERE qr.user_id =:userId AND NOT qr.status = 0 and q.year=:year and q.quarter_number=:quarter and qd.platform like %:platform%  limit :offset,:limit";

    @Query(value = FIND_ARTIST_REPORT_DATA_BY_ARTIST_ID, nativeQuery = true)
    List<Object[]> findArtistReportDataByArtistId(Integer userId,Integer year,Integer quarter,String platform,Integer limit,Integer offset);

    final String COUNT_OF_QUARTER_DATA_BY_ARTIST_ID = "select count(qd.id) from quarter_data qd INNER join quarter_report qr on qd.quarter_report_id=qr.id INNER join quarter q on q.id=qr.quarter_id where qr.user_id =:userId AND NOT qr.status = 0 and q.year=:year and q.quarter_number=:quarter and qd.platform like %:platform%  ";

    @Query(value = COUNT_OF_QUARTER_DATA_BY_ARTIST_ID, nativeQuery = true)
    List<Object[]> getCountOfQuarterDataByArtistId(Integer userId,Integer year,Integer quarter,String platform);

    final String FIND_ARTIST_REPORT_DATA_BY_REPORT_ID = "select qr.id, q.quarter_number,q.year, qd.artist,qd.asset_title,qd.custom_id,qd.platform,qd.revenue,qd.revenue_share,qd.isrc from quarter_data qd INNER join quarter_report qr on qd.quarter_report_id=qr.id INNER join quarter q on q.id=qr.quarter_id where qr.id=?1";

    @Query(value = FIND_ARTIST_REPORT_DATA_BY_REPORT_ID, nativeQuery = true)
    List<Object[]> findArtistReportDataByReportId(Integer reportId);

    final String CONTENT_COUNT = "select count(*) from quarter_data where status=1;";

    @Query(value = CONTENT_COUNT, nativeQuery = true)
    List<Object[]> findContentCount();
}
