package com.eden.enforcementService.repository;
import com.eden.enforcementService.common.model.entity.Penalty;
import com.eden.enforcementService.common.model.enums.PenaltyMethod;
import com.eden.enforcementService.common.model.enums.PenaltyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Collection;

public interface PenaltyRepository extends JpaRepository<Penalty,Long> {

    @Query(value = "SELECT p FROM Penalty as p WHERE (p.enName like concat('%', :searchValue,'%') or " +
            "p.arName like concat('%', :searchValue,'%') or " +
            "concat(p.id,'') like concat('%', :searchValue,'%') or "+
            "concat(p.fees,'') like concat('%', :searchValue,'%') or :searchValue = '') and "+
            "(concat(p.type,'') like concat('%', :typeValue,'%') or :typeValue = '') and "+
            "(concat(p.method,'') like concat('%', :methodValue,'%') or :methodValue = '')"
    )
    Page<Penalty> findAllBySearchValue(String searchValue, String typeValue,
                                       String methodValue, Pageable pageable);

    boolean existsByEnNameOrArName(String enName,String arName);

    Penalty findByEnName(String enName);


    Integer countByIdIn(Collection<Long> ids);

    Boolean existsByEnName(String name);

    Boolean existsByArName(String name);
}
