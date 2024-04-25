package projects.acueductosapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "message")
    private String message;

    @Column(name = "product_id")
    private Integer product_id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

}