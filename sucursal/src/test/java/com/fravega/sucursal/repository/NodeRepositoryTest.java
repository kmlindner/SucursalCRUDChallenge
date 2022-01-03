package com.fravega.sucursal.repository;

import com.fravega.sucursal.model.BranchOffice;
import com.fravega.sucursal.model.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class NodeRepositoryTest {

    @Autowired
    private NodeRepository nodeRepository;

    @Test
    public void testGetNodeById_thenReturnNode() {
        Node node = nodeRepository.save(new BranchOffice(90.0, 90.00, "address", "business_hours"));
        Node found = nodeRepository.getById(node.getId());
        Assertions.assertEquals(node.getId(), found.getId());
    }

}
