package com.jvmless.shop.sales.domain.productcatalog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ProductDetail {
    @Id
    @GeneratedValue
    private Long detailId;
}
