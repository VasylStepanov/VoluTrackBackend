package com.application.volunteers.address.repository;

import com.application.volunteers.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

    Optional<Address> findByVolunteerId(UUID volunteerId);
}
