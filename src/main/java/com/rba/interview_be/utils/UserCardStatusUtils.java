package com.rba.interview_be.utils;

import com.rba.interview_be.entities.UserCardStatusEntity;

public class UserCardStatusUtils {

    public static void cloneStatus(UserCardStatusEntity from, UserCardStatusEntity to){
        to.setId(from.getId());
        to.setStatus(from.getStatus());
        to.setUser(from.getUser());
        to.setCreatedAt(from.getCreatedAt());
    }
}
