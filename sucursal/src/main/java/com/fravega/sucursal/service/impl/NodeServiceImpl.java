package com.fravega.sucursal.service.impl;

import com.fravega.sucursal.model.Node;
import com.fravega.sucursal.repository.NodeRepository;
import com.fravega.sucursal.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class NodeServiceImpl implements NodeService {

    @Autowired
    NodeRepository repository;

    @Override
    public List<Node> getAllNodes() {
        return new ArrayList<>(repository.findAll());
    }

    @Override
    public Optional<Node> getNodeById(int id) {
        return repository.findById(id);
    }

    @Override
    public Node saveNode(Node node) {
        return repository.save(node);
    }

    @Override
    public void deleteNodeById(int id) {
        repository.deleteById(id);
    }

}
