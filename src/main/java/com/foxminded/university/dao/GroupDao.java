package com.foxminded.university.dao;

import com.foxminded.university.entity.Group;

import java.util.List;

public interface GroupDao extends CrudDao<Group, Integer> {
    List<Group> findGroupsByStudentsCount(Integer count);
}
