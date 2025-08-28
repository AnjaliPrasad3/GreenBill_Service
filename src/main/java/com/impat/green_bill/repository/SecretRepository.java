package com.impat.green_bill.repository;
import com.impat.green_bill.entities.Secrets;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SecretRepository extends MongoRepository<Secrets, String>{
    Optional<Secrets> findByName(String name);
}
