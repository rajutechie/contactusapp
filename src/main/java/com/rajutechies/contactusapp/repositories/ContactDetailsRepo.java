package com.rajutechies.contactusapp.repositories;

import com.rajutechies.contactusapp.models.request.ContactDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactDetailsRepo extends MongoRepository<ContactDetails, String> {

    List<ContactDetails> findContactDetailsByOrderByCreatedOnDesc();
}
