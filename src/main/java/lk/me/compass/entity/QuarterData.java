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
@Entity(name="quarter_data")
public class QuarterData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "artist")
    private String artist;

    @Column(name = "ISRC")
    private String isrc;

    @Column(name = "asset_title")
    private String assetTitle;

    @Column(name = "platform")
    private String platform;

    @Column(name = "revenue")
    private Double revenue;

    @Column(name = "revenue_share")
    private Integer revenueShare;

    @Column(name = "status")
    private Integer status;

    @CreationTimestamp
    @Column(name = "create_date", columnDefinition = "TIMESTAMP")
    private Date createDate;

    @Column(name = "custom_id")
    private String customId;
    
    @ManyToOne
    @JoinColumn(name = "quarter_report_id")
    private QuarterReport quarterReport;

}
