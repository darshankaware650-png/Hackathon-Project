package com.hackathon.controller;

import com.hackathon.model.Opportunity;
import com.hackathon.service.OpportunityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opportunities")
public class OpportunityController {

    private final OpportunityService service;

    public OpportunityController(OpportunityService service) {
        this.service = service;
    }

    @GetMapping
    public List<Opportunity> list(@RequestParam(required = false) String category,
                                   @RequestParam(required = false) String search) {
        return service.findAll(category, search);
    }

    @GetMapping("/{id}")
    public Opportunity get(@PathVariable String id) {
        return service.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @PostMapping
    public Opportunity create(@RequestBody Opportunity opportunity) {
        return service.create(opportunity);
    }

    @PostMapping("/recommend")
    public List<Opportunity> recommend(@RequestBody RecommendRequest request) {
        return service.recommendFor(request.skills(), request.interests());
    }

    public record RecommendRequest(List<String> skills, List<String> interests) {
    }
}
