package projects.acueductosapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Column(name = "username", length = 100, unique = true)
    private String username;

    @Size(max = 100)
    @Column(name = "address", length = 100)
    private String address;

    @Size(max = 100)
    @Column(name = "city", length = 100)
    private String city;

    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "enabled")
    private Boolean enabled;

    @Size(max = 100)
    @Column(name = "password", length = 100)
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Size(max = 150)
    @Column(name = "cellphone", length = 150)
    private String cellphone;

    @Size(max = 150)
    @Column(name = "first_name", length = 150)
    private String firstName;

    @Size(max = 150)
    @Column(name = "last_name", length = 150)
    private String lastName;

}