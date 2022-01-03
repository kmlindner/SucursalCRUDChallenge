package com.fravega.sucursal.service;

import com.fravega.sucursal.model.Node;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("nodeService")
public interface NodeService {

    List<Node> getAllNodes();
    Optional<Node> getNodeById(int id);
    Node saveNode(Node node);
    void deleteNodeById(int id);

}
