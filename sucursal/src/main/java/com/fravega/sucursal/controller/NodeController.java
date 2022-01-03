package com.fravega.sucursal.controller;

import com.fravega.sucursal.model.BranchOffice;
import com.fravega.sucursal.model.Node;
import com.fravega.sucursal.model.WithdrawalPoint;
import com.fravega.sucursal.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class NodeController {

    @Autowired
    private NodeService nodeService;

    @GetMapping("/nodes")
    public ResponseEntity<List<Node>> getAllNodes() {
        try {
            List<Node> nodes = nodeService.getAllNodes();
            if (nodes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(nodes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/nodes/{id}")
    public ResponseEntity<Node> getNodeById(@PathVariable("id") int id) {
        Optional<Node> nodeData = nodeService.getNodeById(id);
        if (nodeData.isPresent()) {
            return new ResponseEntity<>(nodeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/nodes/closest")
    public ResponseEntity<Node> getClosestNode(@RequestParam (value="latitude") double latitude, @RequestParam (value="longitude") double longitude) {
        try {
            Node node = nodeService.getClosestNode(latitude, longitude);
            return new ResponseEntity<>(node, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/nodes/branch-office")
    public ResponseEntity<Node> createBranchOffice(@RequestBody BranchOffice branchOffice) {
        try {
            Node branchOfficeNode = nodeService.saveNode(new BranchOffice(branchOffice.getLatitude(), branchOffice.getLongitude(), branchOffice.getAddress(), branchOffice.getBusinessHours()));
            return new ResponseEntity<>(branchOfficeNode, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/nodes/withdrawal-point")
    public ResponseEntity<Node> createWithdrawalPoint(@RequestBody WithdrawalPoint withdrawalPoint) {
        try {
            Node withdrawalPointNode = nodeService.saveNode(new WithdrawalPoint(withdrawalPoint.getLatitude(), withdrawalPoint.getLongitude(), withdrawalPoint.getCapacity()));
            return new ResponseEntity<>(withdrawalPointNode, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/nodes/branch-office/{id}")
    public ResponseEntity<Node> updateBranchOffice(@PathVariable("id") int id, @RequestBody BranchOffice branchOffice) {
        Optional<Node> nodeData = nodeService.getNodeById(id);
        if (nodeData.isPresent()) {
            BranchOffice branchOfficeNode = (BranchOffice) nodeData.get();
            branchOfficeNode.setLatitude(branchOffice.getLatitude());
            branchOfficeNode.setLongitude(branchOffice.getLongitude());
            branchOfficeNode.setAddress(branchOffice.getAddress());
            branchOfficeNode.setBusinessHours(branchOffice.getBusinessHours());
            return new ResponseEntity<>(nodeService.saveNode(branchOfficeNode), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/nodes/withdrawal-point/{id}")
    public ResponseEntity<Node> updateWithdrawalPoint(@PathVariable("id") int id, @RequestBody WithdrawalPoint withdrawalPoint) {
        Optional<Node> nodeData = nodeService.getNodeById(id);
        if (nodeData.isPresent()) {
            WithdrawalPoint withdrawalPointNode = (WithdrawalPoint) nodeData.get();
            withdrawalPointNode.setLatitude(withdrawalPoint.getLatitude());
            withdrawalPointNode.setLongitude(withdrawalPoint.getLongitude());
            withdrawalPointNode.setCapacity(withdrawalPoint.getCapacity());
            return new ResponseEntity<>(nodeService.saveNode(withdrawalPointNode), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/nodes/{id}")
    public ResponseEntity<HttpStatus> deleteNode(@PathVariable("id") int id) {
        try {
            nodeService.deleteNodeById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
