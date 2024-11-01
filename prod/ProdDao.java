package com.example.demo.prod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProdDao extends JpaRepository<Prod, Integer> {

}
