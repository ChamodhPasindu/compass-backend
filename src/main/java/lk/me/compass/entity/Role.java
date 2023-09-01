package lk.me.compass.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Entity(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "status")
    private Integer status;

    @CreationTimestamp
    @Column(name = "created_date", columnDefinition = "TIMESTAMP")
    private Date createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date", columnDefinition = "TIMESTAMP")
    private Date updatedDate;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<User> user = new ArrayList<>();
}
