package bts.KCamps.repository.impl;

import bts.KCamps.dto.CampIdDescriptionDto;
import bts.KCamps.dto.CampIdLocationDto;
import bts.KCamps.model.Camp;
import bts.KCamps.repository.CustomCampRepo;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable("description")
    public List<CampIdDescriptionDto> getDescriptions() {
        TypedQuery<Tuple> query = em.createNamedQuery(Camp.GET_DESCRIPTIONS, Tuple.class);

        return query.getResultList()
                .stream()
                .map(t -> new CampIdDescriptionDto(t.get(0, Long.class), t.get(1, String.class)))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable("location")
    public List<CampIdLocationDto> getCampsCoordinate() {
        TypedQuery<Tuple> query = em.createNamedQuery(Camp.GET_COORDINATES, Tuple.class);

        return query.getResultList()
                .stream()
                .map(t -> new CampIdLocationDto(t.get(0, Long.class), t.get(1, String.class), t.get(2, String.class)))
                .collect(Collectors.toList());
    }
}
