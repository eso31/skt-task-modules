package com.skytask.repository;

import com.skytask.common.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Procedure
    int insertProduct(String name, String description, Double price, Integer stock);

    @Query(nativeQuery = true, value = "SELECT * from  getproducts()")
    List<Product> getProducts();
}
