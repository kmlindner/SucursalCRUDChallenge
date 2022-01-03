package com.fravega.sucursal.service;

import com.fravega.sucursal.model.BranchOffice;
import com.fravega.sucursal.model.Node;
import com.fravega.sucursal.repository.NodeRepository;
import com.fravega.sucursal.service.impl.NodeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class NodeServiceImplTest {

    @Mock
    private NodeRepository nodeRepository;
    @InjectMocks
    private NodeServiceImpl nodeService;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllNodes() {
        nodeService.getAllNodes();
        verify(nodeRepository).findAll();
    }

    @Test
    void canGetNodeByID() {
        nodeService.getNodeById(1);
        verify(nodeRepository).findById(1);
    }

    @Test
    void canSaveNode() {
        Node node = new BranchOffice(90.00, 90.00, "address", "business_hours");
        nodeService.saveNode(node);
        ArgumentCaptor<Node> nodeArgumentCaptor = ArgumentCaptor.forClass(Node.class);
        verify(nodeRepository).save(nodeArgumentCaptor.capture());
        Node capturedNode = nodeArgumentCaptor.getValue();
        Assertions.assertEquals(capturedNode, node);
    }

    @Test
    void canDeleteNode() {
        Node node = new BranchOffice(90.00, 90.00, "address", "business_hours");
        nodeService.deleteNodeById(node.getId());
        verify(nodeRepository).deleteById(node.getId());
    }

    @Test
    void canGetClosestNode() {
        Node farNode = new BranchOffice(90.00, 180.00, "farAway", "business_hours");
        Node nearNode = new BranchOffice(10.00, 10.00, "prettyNear", "business_hours");
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(farNode);
        nodeList.add(nearNode);
        Mockito.when(nodeRepository.findAll()).thenReturn(nodeList);
        Node closestNode = nodeService.getClosestNode(5.00, 5.00);
        Assertions.assertEquals(nearNode, closestNode);
    }
}
