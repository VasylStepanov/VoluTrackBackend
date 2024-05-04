package com.application.volunteers.request.repository;

import com.application.volunteers.request.model.RequestItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RequestItemRepository extends JpaRepository<RequestItem, UUID> {
}
