package lk.me.compass.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="report_log_data")
public class ReportLogData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "IP_address")
    private String ipAddress;

    @Column(name = "status")
    private Integer status;
    
    @CreationTimestamp
    @Column(name = "download_date", columnDefinition = "TIMESTAMP")
    private Date downloadDate;

    @ManyToOne
    @JoinColumn(name = "quarter_report_id")
    private QuarterReport quarterReport;

}
