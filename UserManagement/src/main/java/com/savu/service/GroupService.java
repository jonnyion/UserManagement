package com.savu.service;

import java.util.List;
import java.util.Optional;

import com.savu.model.Group;

public interface GroupService {

	Optional<Group> findById(Long id);

	Group findByName(String name);

	void saveGroup(Group user);

	void updateGroup(Group user);

	void deleteGroupById(Long id);

	void deleteAllGroups();

	List<Group> findAllGroups();

	boolean isGroupExist(Group user);
}
