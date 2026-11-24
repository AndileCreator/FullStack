package com.example.FullStack.Controller;

import com.example.FullStack.Dto.PeopleRequest;
import com.example.FullStack.Dto.PeopleResponse;
import com.example.FullStack.Service.PeopleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
public class PeopleController {

    private final PeopleService peopleService;
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }
    //Create
    @PostMapping
    public ResponseEntity<PeopleResponse> createPeople(
            @Valid @RequestBody PeopleRequest peopleRequest) {

        PeopleResponse peopleResponse =
                peopleService.createPeople(peopleRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(peopleResponse);
    }

    //Read All
    @GetMapping
    public ResponseEntity<List<PeopleResponse>> getAllPeople() {

        return ResponseEntity
                .ok(peopleService.getAllPeople());
    }

    //Read One
    @GetMapping("/{id}")
    public ResponseEntity<PeopleResponse> getPeopleById(@PathVariable Long id) {

        return ResponseEntity
                .ok(peopleService.getPeopleById(id));
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<PeopleResponse> updatePeople(
            @PathVariable Long id,
            @Valid @RequestBody PeopleRequest peopleRequest
    ) {
        return  ResponseEntity
                .ok(peopleService.updatePeopleById(id, peopleRequest));
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeopleById(@PathVariable Long id) {
        peopleService.deletePeopleById(id);
        return ResponseEntity.noContent().build();
    }
}
