package com.savu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.savu.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
	Group findByName(String name);

}
