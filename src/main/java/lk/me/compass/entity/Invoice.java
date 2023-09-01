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
@Entity(name="invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "invoice_id")
    private String invoiceId;

    @Column(name = "issue_date", columnDefinition = "TIMESTAMP")
    private Date issueDate;

    @Column(name = "cheque_date", columnDefinition = "TIMESTAMP")
    private Date chequeDate;

    @CreationTimestamp
    @Column(name = "create_date", columnDefinition = "TIMESTAMP")
    private Date createDate;

    // @Column(name = "date_period")
    // private String datePeriod;

    @Column(name = "status")
    private int status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "quarter_id")
    private Quarter quarterId;
}
