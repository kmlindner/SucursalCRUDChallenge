package com.fravega.sucursal.repository;

import com.fravega.sucursal.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeRepository extends JpaRepository<Node, Integer> {
    
}
