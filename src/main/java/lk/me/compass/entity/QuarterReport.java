package lk.me.compass.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="quarter_report")
public class QuarterReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "status")
    private Integer status;

    @CreationTimestamp
    @Column(name = "create_date", columnDefinition = "TIMESTAMP")
    private Date createDate;

    @Column(name = "update_date", columnDefinition = "TIMESTAMP")
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "quarter_id")
    private Quarter quarterId;

    @OneToMany(mappedBy = "id", cascade = CascadeType.REMOVE)
    private List<ReportLogData> reportLogData = new ArrayList<>();

    @OneToMany(mappedBy = "id", cascade = CascadeType.REMOVE)
    private List<QuarterData> quarterData = new ArrayList<>();

}
