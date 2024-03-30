package projects.acueductosapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @Size(max = 100)
    @Column(name = "username", nullable = false, length = 100)
    private String username;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties( {"hibernateLazyInitializer", "handler"})
    private User user;

    @Size(max = 100)
    @NotNull
    @Column(name = "authority", nullable = false, length = 100)
    private String authority;

}