package com.fpoly.mlbshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "imgSlide")
public class imgSlide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    @Column(name = "imgSlide")
    String imgSlide;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idProduct", referencedColumnName = "idProduct")
    @JsonBackReference
    Product product;

    @Override
    public String toString() {
        return "imgSlide{" +
                "id=" + id +
                ", imgSlide='" + imgSlide + '\'' +
                '}';
    }
}
