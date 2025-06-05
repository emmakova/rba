package com.rba.interview_be.service.queryspecifications;


import com.rba.interview_be.controller.filter.SearchUserFilter;
import com.rba.interview_be.entities.UserEntity;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersQueryPredicateService {

    private UsersQueryPredicateService() {}

    public static Predicate getUsersQueryPredicates(
            Root<UserEntity> root,
            CriteriaQuery<?> cq,
            CriteriaBuilder builder,
            SearchUserFilter filter
    ) {

        List<Predicate> predicates = new ArrayList<>();

        Optional.ofNullable(filter.oib()).ifPresent(oib ->
                predicates.add(builder.like(root.get("oib"), oib + "%")));

        root.fetch("cardStatuses", JoinType.LEFT);

        return builder.and(predicates.toArray(new Predicate[0]));
    }

}
