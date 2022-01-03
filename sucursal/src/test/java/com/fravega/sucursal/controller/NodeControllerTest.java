package com.fravega.sucursal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fravega.sucursal.model.BranchOffice;
import com.fravega.sucursal.model.Node;
import com.fravega.sucursal.service.impl.NodeServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = NodeController.class)
public class NodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NodeServiceImpl nodeService;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldReturnAllNodes() throws Exception {
        List<Node> nodeList = new ArrayList<>();
        Node node = new BranchOffice(90.00, 90.00, "Av Test 1234", "9:00 - 18:00");
        nodeList.add(node);
        Mockito.when(nodeService.getAllNodes()).thenReturn(nodeList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/nodes")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].address", Matchers.equalTo("Av Test 1234")));
    }

    @Test
    void shouldReturnNoContent() throws Exception {
        List<Node> nodeList = new ArrayList<>();
        Mockito.when(nodeService.getAllNodes()).thenReturn(nodeList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/nodes")).andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNodeById() throws Exception {
        Optional<Node> node = Optional.of(new BranchOffice(90.00, 90.00, "Av Test 1234", "9:00 - 18:00"));
        Mockito.when(nodeService.getNodeById(1)).thenReturn(node);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/nodes/{id}", "1")).andExpect(status().isOk());
    }

    @Test
    void shouldReturnClosestNode() throws Exception {
        Node farNode = new BranchOffice(90.00, 180.00, "farAway", "business_hours");
        Node nearNode = new BranchOffice(10.00, 10.00, "prettyNear", "business_hours");
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(farNode);
        nodeList.add(nearNode);
        Mockito.when(nodeService.getAllNodes()).thenReturn(nodeList);
        Mockito.when(nodeService.getClosestNode(5.00,5.00)).thenReturn(nearNode);
        String json = mapper.writeValueAsString(nearNode);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/nodes/closest")
                .param("latitude","5.00").param("longitude", "5.00")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.latitude", Matchers.equalTo(10.0)))
                .andExpect(jsonPath("$.longitude", Matchers.equalTo(10.0)));
    }

    @Test
    void shouldSaveNode() throws Exception {
        Node node = new BranchOffice(90.00, 90.00, "Av Test 1234", "9:00 - 18:00");
        node.setId(1);
        Mockito.when(nodeService.saveNode(ArgumentMatchers.any())).thenReturn(node);
        String json = mapper.writeValueAsString(node);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/nodes/branch-office").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.address", Matchers.equalTo("Av Test 1234")));
    }

    @Test
    void shouldUpdateNode() throws Exception {
        Optional<Node> node = Optional.of(new BranchOffice(90.00, 90.00, "Av Test 1234", "9:00 - 18:00"));
        Mockito.when(nodeService.getNodeById(1)).thenReturn(node);
        String json = mapper.writeValueAsString(node.get());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/nodes/branch-office/{id}","1").characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteNodeById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/nodes/{id}", "1")).andExpect(status().isNoContent());
    }

}
