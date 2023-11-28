package com.ecommerce.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "product_images")
@Data
@NoArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;
    private String originalFilename;
    private String systemFilename;
    private String mimetype;
    @Column(name = "display_order")
    private int order;

    public ProductImage(Product product, String originalFilename, String mimetype, int order) {
        this.product = product;
        this.originalFilename = originalFilename;
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        this.systemFilename = UUID.randomUUID()+ fileExtension;
        this.mimetype = mimetype;
        this.order = order;
    }
}
