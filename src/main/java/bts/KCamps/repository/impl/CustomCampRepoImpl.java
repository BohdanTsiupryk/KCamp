package bts.KCamps.repository.impl;

import bts.KCamps.dto.CampIdDescriptionDto;
import bts.KCamps.model.Camp;
import bts.KCamps.repository.CustomCampRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomCampRepoImpl implements CustomCampRepo {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<CampIdDescriptionDto> getDescriptions() {
        TypedQuery<Tuple> query = em.createNamedQuery(Camp.GET_DESCRIPTIONS, Tuple.class);

        return query.getResultList()
                .stream()
                .map(t -> new CampIdDescriptionDto(t.get(0, Long.class), t.get(1, String.class)))
                .collect(Collectors.toList());
    }
}
