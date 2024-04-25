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
@Table(name = "blog_posts")
public class BlogPost {
    @Transient
    private String imageBase64;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Integer id;


    @JoinColumn(name = "user_id")
    private Integer user_id;

    @Size(max = 100)
    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "content", length = Integer.MAX_VALUE)
    private String content;



    @Column(name = "image")
    private byte[] image;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

}