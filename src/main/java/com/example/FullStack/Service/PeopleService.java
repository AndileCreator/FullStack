package com.example.FullStack.Service;


import com.example.FullStack.Dto.PeopleRequest;
import com.example.FullStack.Dto.PeopleResponse;
import com.example.FullStack.Entity.PeopleEntity;
import com.example.FullStack.Exception.DublicateResourceException;
import com.example.FullStack.Exception.ResourceNotFoundException;
import com.example.FullStack.Repository.PeopleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PeopleService {
    private final PeopleRepository peopleRepository;
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    //Create
    @Transactional
    public PeopleResponse createPeople(PeopleRequest peopleRequest) {
        if (peopleRepository.existsByEmail(peopleRequest.email())){
            throw new DublicateResourceException("Email already exists");
        }

        PeopleEntity people = new PeopleEntity();
        people.setEmail(peopleRequest.email());
        people.setName(peopleRequest.name());

        PeopleEntity newPeople = peopleRepository.save(people);
        return mapToResponse(newPeople);
    }
    //Read All
    @Transactional(readOnly = true)
    public List<PeopleResponse>getAllPeople(){
        return peopleRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    //Read All
    @Transactional(readOnly = true)
    public PeopleResponse getPeopleById(Long id){

        PeopleEntity people = findPeopleOrThrow(id);
        return mapToResponse(people);
    }

    //Update
    @Transactional
    public PeopleResponse updatePeopleById(Long id, PeopleRequest peopleRequest) {

        PeopleEntity people = findPeopleOrThrow(id);

        boolean emailUsedByAnotherPeople =
                peopleRepository.existsByEmailAndIdNot(
                        peopleRequest.email(),
                        id
                );

        if (emailUsedByAnotherPeople){
            throw new DublicateResourceException("Email already exists");
        }

        people.setName(peopleRequest.name());
        people.setEmail(peopleRequest.email());

        PeopleEntity updatedPeople = peopleRepository.save(people);
        return mapToResponse(updatedPeople);
    }

    @Transactional
    public void deletePeopleById(Long id){
        PeopleEntity people = findPeopleOrThrow(id);
        peopleRepository.delete(people);
    }

    private PeopleEntity findPeopleOrThrow(Long id){

        return peopleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "People not found by ID: " + id
        ));
    }
    private PeopleResponse mapToResponse(PeopleEntity people){
        return new PeopleResponse(
                people.getId(),
                people.getName(),
                people.getEmail()
        );
    }
}
