package lk.me.compass.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import lk.me.compass.entity.QuarterReport;

@Repository
public interface QuarterReportRepo extends JpaRepository<QuarterReport, Integer> {

    final String IS_EXIST_QUARTER_REPORT = "select * from quarter_report where user_id=?1 and quarter_id=?2";

    @Query(value = IS_EXIST_QUARTER_REPORT, nativeQuery = true)
    Optional<QuarterReport> findQuarterReportByUserIdAndQuarterId(Integer userId, Integer quarterId);

    final String FIND_ALL_ARTIST_REPORT = "SELECT u.sap_vender_no, q.year, q.quarter_number, i.invoice_id, u.sap_vender_name, DATE(i.cheque_date), qr.status, qr.id, sum(qd.revenue) FROM user u INNER JOIN quarter_report qr ON u.id = qr.user_id INNER JOIN quarter q ON q.id = qr.quarter_id INNER JOIN quarter_data qd ON qd.quarter_report_id = qr.id LEFT outer JOIN invoice i on i.user_id = u.id WHERE CONCAT(u.sap_vender_name, u.sap_vender_no) LIKE %:search% AND NOT qr.status = 0 AND NOT qr.status = 4 AND NOT qr.status = 1 AND NOT u.role_id = 2 AND q.year LIKE %:year% AND q.quarter_number LIKE %:quarter% group by i.invoice_id,sap_vender_no,q.year,q.quarter_number,u.sap_vender_name,qr.status,qr.id,i.cheque_date limit :offset,:limit";

    @Query(value = FIND_ALL_ARTIST_REPORT, nativeQuery = true)
    List<Object[]> findAllArtistReports(String year, String quarter, String search, Integer limit, Integer offset);

    final String FIND_ALL_ARTIST_REPORT_COUNT = "SELECT COUNT(*) FROM quarter_report qr INNER JOIN user u ON u.id = qr.user_id INNER JOIN quarter q ON q.id = qr.quarter_id LEFT JOIN invoice i ON i.quarter_id = q.id WHERE concat(u.sap_vender_name,u.sap_vender_no) like %:search% AND NOT qr.status = 0 AND NOT qr.status = 4 AND NOT qr.status = 1 AND NOT u.role_id = 2 AND q.year LIKE %:year% AND q.quarter_number LIKE %:quarter%";

    @Query(value = FIND_ALL_ARTIST_REPORT_COUNT, nativeQuery = true)
    List<Object[]> findAllArtistReportsCount(String year, String quarter, String search);

    final String FIND_ALL_ARTIST_PAYMENT_HISTORY = "SELECT q.quarter_number, q.year, qr.status,sum(qd.revenue) FROM user u INNER JOIN quarter_report qr ON u.id = qr.user_id INNER JOIN quarter q ON q.id = qr.quarter_id INNER JOIN quarter_data qd ON qd.quarter_report_id=qr.id WHERE u.sap_vender_no = ?1 group by q.quarter_number,q.year,qr.status ORDER BY q.year DESC LIMIT 12";

    @Query(value = FIND_ALL_ARTIST_PAYMENT_HISTORY, nativeQuery = true)
    List<Object[]> findAllArtistPaymentHistory(String venderNo);

    final String TRANSACTION_COUNT = "select count(*) from quarter_report where status=4";

    @Query(value = TRANSACTION_COUNT, nativeQuery = true)
    List<Object[]> findTransactionCount();

    final String TOTAL_REVENUE = "select sum(revenue) from quarter_data where quarter_report_id=?1";

    @Query(value = TOTAL_REVENUE, nativeQuery = true)
    List<Object[]> findTotalRevenue(Integer reportId);

    final String TOTAL_QUARTER_PAYMENT = "select sum(qd.revenue) from quarter_data qd inner join quarter_report qr on qd.quarter_report_id=qr.id inner join user u on u.id=qr.user_id inner join quarter q on q.id=qr.quarter_id where q.quarter_number=:quarter and q.year=:year and u.sap_vender_no=:vendorNo";

    @Query(value = TOTAL_QUARTER_PAYMENT, nativeQuery = true)
    List<Object[]> findQuarterPayment(Integer year, Integer quarter, String vendorNo);
}
