package com.fravega.sucursal.service.impl;

import com.fravega.sucursal.model.Node;
import com.fravega.sucursal.repository.NodeRepository;
import com.fravega.sucursal.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

@Component
public class NodeServiceImpl implements NodeService {

    @Autowired
    NodeRepository repository;

    public static final int EARTH_RADIUS = 6371;

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

    @Override
    public Node getClosestNode(double latitude, double longitude) {
        List<Node> nodeList = repository.findAll();
        TreeMap<Double, Node> nodeTreeMap = new TreeMap<>();
        nodeList.forEach(node -> nodeTreeMap.put(calculateHaversineAlgorithm(node.getLatitude(), node.getLongitude(), latitude, longitude), node));
        return nodeTreeMap.firstEntry().getValue();
    }

    static double calculateHaversineAlgorithm(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.pow(Math.sin(dLon / 2), 2)
                * Math.cos(lat1)
                * Math.cos(lat2);

        double c = 2 * Math.asin(Math.sqrt(a));

        return EARTH_RADIUS * c;
    }

}
