package com.eden.enforcementService.common.specs;

import com.eden.enforcementService.common.model.entity.Citation;
import com.eden.enforcementService.common.model.enums.CitationStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class CitaionSpecs {
    public static Specification<Citation> distinct() {
        return (root, query, cb) -> {
            query.distinct(true);
            return null;
        };
    }

    public static Specification<Citation> citationByPlateNumberAR(String name) {
        return (root, query, builder) ->
                Objects.isNull(name) ? builder.conjunction() :
                        builder.like(builder.lower(root.get("plateNumberAr")), String.format("%s%s%s", "%", name.toLowerCase(), "%"));
    }

    public static Specification<Citation> citationByPlateNumberEN(String name) {
        return (root, query, builder) ->
                Objects.isNull(name) ? builder.conjunction() :
                        builder.like(builder.lower(root.get("plateNumberEn")), String.format("%s%s%s", "%", name.toLowerCase(), "%"));
    }

    public static Specification<Citation> openCitation(List<CitationStatus> statusList) {
        return (root, query, builder) ->
                Objects.isNull(statusList) || statusList.isEmpty() ? builder.conjunction() :
                        root.get("status").in(statusList);
    }

//    public static Specification<Citation> eNameOrder(String ename) {
//        return (root, query, builder) ->
//                Objects.isNull(ename) || !ename.equalsIgnoreCase("enName") ? builder.conjunction() :
//                        builder.equal(query.orderBy(builder.asc(root.join("citationPenalties").get("operationViolationPenalty")
//                        .get("violation").get("enName"))));
//
//
//    }

    public static Specification<Citation> citationByNumber(String cId) {
        Long id = null;

        if (!Objects.isNull(cId)) {
            try {
                id = Long.parseLong(cId);
            } catch (NumberFormatException nfe) {

            }
        }
        Long finalId = id;
        return (root, query, builder) ->
                Objects.isNull(cId) ? builder.conjunction() :
                        builder.equal(root.get("id"), finalId);
    }

}
