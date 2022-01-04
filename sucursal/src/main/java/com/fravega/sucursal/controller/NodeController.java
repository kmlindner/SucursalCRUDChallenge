package com.fravega.sucursal.controller;

import com.fravega.sucursal.model.BranchOffice;
import com.fravega.sucursal.model.Node;
import com.fravega.sucursal.model.WithdrawalPoint;
import com.fravega.sucursal.service.NodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Tag(name = "Node Controller", description = "Node API Rest")
public class NodeController {

    @Autowired
    private NodeService nodeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeController.class);

    @Operation(summary = "Get all nodes", description = "Includes both node types, branch offices and withdrawal points", tags = "Node Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all nodes successfully", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(oneOf = { BranchOffice.class, WithdrawalPoint.class }))}),
            @ApiResponse(responseCode = "204", description = "No content found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
    @GetMapping("/nodes")
    public ResponseEntity<List<Node>> getAllNodes() {
        try {
            List<Node> nodes = nodeService.getAllNodes();
            if (nodes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(nodes, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error on getAllNodes method: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get node by ID", description = "Returns a single node, can be either branch office or withdrawal point type", tags = "Node Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found node successfully.", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(oneOf = { BranchOffice.class, WithdrawalPoint.class }))}),
            @ApiResponse(responseCode = "204", description = "No content found.", content = @Content) })
    @GetMapping("/nodes/{id}")
    public ResponseEntity<Node> getNodeById(
            @Parameter(description="Id of the node to be obtained. Cannot be empty.", required=true)
            @PathVariable("id") int id) {
        try {
            Optional<Node> nodeData = nodeService.getNodeById(id);
            if (nodeData.isPresent()) {
                return new ResponseEntity<>(nodeData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOGGER.error("Error on getNodeById method: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get closest node", description = "Returns closest node to provided point", tags = "Node Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found closest node successfully.", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(oneOf = { BranchOffice.class, WithdrawalPoint.class }))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
    @GetMapping("/nodes/closest")
    public ResponseEntity<Node> getClosestNode(
            @Parameter(description="Latitude of provided point.", required=true)
            @RequestParam (value="latitude") double latitude,
            @Parameter(description="Longitude of provided point.", required=true)
            @RequestParam (value="longitude") double longitude) {
        try {
            Node node = nodeService.getClosestNode(latitude, longitude);
            return new ResponseEntity<>(node, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error on getClosestNode method: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create new branch office", description = "Creates a new node of branch office type", tags = "Node Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Branch office created successfully", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema( implementation = BranchOffice.class ))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "409", description = "Node already exists", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
    @PostMapping("/nodes/branch-office")
    public ResponseEntity<Node> createBranchOffice(
            @Parameter(description = "Branch office to create. Latitude and longitude cannot be null.", required = true, schema = @Schema( implementation = BranchOffice.class))
            @Valid @RequestBody BranchOffice branchOffice) {
        try {
            Node branchOfficeNode = nodeService.saveNode(new BranchOffice(branchOffice.getLatitude(), branchOffice.getLongitude(), branchOffice.getAddress(), branchOffice.getBusinessHours()));
            return new ResponseEntity<>(branchOfficeNode, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error on createbranchOffice method: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create new withdrawal point", description = "Creates a new node of withdrawal point type", tags = "Node Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Withdrawal point created successfully", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema( implementation = WithdrawalPoint.class ))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "409", description = "Node already exists", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
    @PostMapping("/nodes/withdrawal-point")
    public ResponseEntity<Node> createWithdrawalPoint(
            @Parameter(description = "Withdrawal point to create. Latitude and longitude cannot be null.", required = true, schema = @Schema( implementation = WithdrawalPoint.class))
            @Valid @RequestBody WithdrawalPoint withdrawalPoint) {
        try {
            Node withdrawalPointNode = nodeService.saveNode(new WithdrawalPoint(withdrawalPoint.getLatitude(), withdrawalPoint.getLongitude(), withdrawalPoint.getCapacity()));
            return new ResponseEntity<>(withdrawalPointNode, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error on createWithdrawalPoint method: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update branch office", description = "Updates an existing node of branch office type with provided ID", tags = "Node Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Branch office updated successfully", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema( implementation = BranchOffice.class ))}),
            @ApiResponse(responseCode = "400", description = "Invalid ID provided", content = @Content),
            @ApiResponse(responseCode = "404", description = "Node not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
    @PutMapping("/nodes/branch-office/{id}")
    public ResponseEntity<Node> updateBranchOffice(
            @Parameter(description = "ID of the branch office to update. Cannot be null.")
            @PathVariable("id") int id,
            @Parameter(description = "Branch office to update. Cannot be null.", required = true, schema = @Schema(implementation = BranchOffice.class))
            @RequestBody BranchOffice branchOffice) {
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

    @Operation(summary = "Update withdrawal point", description = "Updates an existing node of withdrawal point type with provided ID", tags = "Node Controller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Withdrawal point successfully updated", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema( implementation = WithdrawalPoint.class ))}),
            @ApiResponse(responseCode = "400", description = "Invalid ID provided", content = @Content),
            @ApiResponse(responseCode = "404", description = "Node not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
    @PutMapping("/nodes/withdrawal-point/{id}")
    public ResponseEntity<Node> updateWithdrawalPoint(
            @Parameter(description = "ID of the withdrawal point to update. Cannot be null.")
            @PathVariable("id") int id,
            @Parameter(description = "Withdrawal point to update. Cannot be null.", required = true, schema = @Schema(implementation = WithdrawalPoint.class))
            @RequestBody WithdrawalPoint withdrawalPoint) {
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

    @Operation(summary = "Delete node", description = "Deletes a node with provided ID", tags = { "Node Controller" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Node successfully deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Node not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
    @DeleteMapping("/nodes/{id}")
    public ResponseEntity<HttpStatus> deleteNode(
            @Parameter(description = "ID of the node to delete . Cannot be null.")
            @PathVariable("id") int id) {
        try {
            nodeService.deleteNodeById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error on deleteNode method: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
