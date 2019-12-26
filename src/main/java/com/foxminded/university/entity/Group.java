package com.foxminded.university.entity;

import java.util.Objects;

public final class Group {

    private final Integer groupId;
    private final String groupName;

    public Group(Integer groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public Group(Integer groupId) {
        this.groupId = groupId;
        this.groupName = null;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(" ").append(groupId)
                .append(" ").append(groupName)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Group group = (Group) o;
        return Objects.equals(groupId, group.groupId) &&
                Objects.equals(groupName, group.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, groupName);
    }
}
