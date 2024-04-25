package projects.acueductosapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Transient
    private String imageBase64;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "image")
    private byte[] image;

    @Column(name = "stock")
    private Integer stock;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;


    @Column(name = "id_category", nullable = false)
    private Integer id_category;

}