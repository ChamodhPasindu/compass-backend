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
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="quarter")

public class Quarter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "quarter_number")
    private Integer quarterNumber;

    @Column(name = "status")
    private Integer status;
    
    @Column(name = "year")
    private Integer year;

    @CreationTimestamp
    @Column(name = "create_date", columnDefinition = "TIMESTAMP")
    private Date createDate;
    
    @OneToMany(mappedBy = "id", cascade = CascadeType.REMOVE)
    private List<QuarterReport> quarterReport = new ArrayList<>();

    @OneToMany(mappedBy = "id", cascade = CascadeType.REMOVE)
    private List<Invoice> invoices = new ArrayList<>();
    
}
