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
@Entity(name="log_data")

public class LogData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "IP_Address")
    private String ipAddress;

    @Column(name = "logout_time", columnDefinition = "TIMESTAMP")
    private Date logoutTime;

    @CreationTimestamp
    @Column(name = "login_time", columnDefinition = "TIMESTAMP")
    private Date loginTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
