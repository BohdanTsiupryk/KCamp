package bts.KCamps.service.impl;

import bts.KCamps.dto.CampIdDescriptionDto;
import bts.KCamps.model.Camp;
import bts.KCamps.repository.CampRepo;
import bts.KCamps.service.CampService;
import bts.KCamps.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final CampRepo campRepo;
    private final CampService campService;

    @Override
    public List<Camp> findByParameters(String param) {
        if (param.isEmpty()) {
            return campRepo.findAll();
        }
        List<CampIdDescriptionDto> allDescriptions = campService.getAllDescriptions();
        Set<String> words = Arrays.stream(param.split(" "))
                .map(String::trim)
                .collect(Collectors.toSet());

        return calculateWage(allDescriptions, words)
                .map(c -> Pair.of(c.getWage(), campService.getById(c.getId())))
                .sorted(Comparator.comparingInt(Pair::getFirst))
                .map(Pair::getSecond)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private Stream<CampIdDescriptionDto> calculateWage(List<CampIdDescriptionDto> descriptionDtos, Set<String> words) {
        return descriptionDtos.stream()
                .peek(desc -> words.forEach(w -> checkContains(desc, w)))
                .filter(CampIdDescriptionDto::isWageNotZero)
                .sorted(Comparator.comparingInt(CampIdDescriptionDto::getWage));
    }

    private void checkContains(CampIdDescriptionDto desc, String word) {
        String description = desc.getDescription();
        int index = 0;
        while (true) {
            int res = description.indexOf(word, index);
            if (res == -1 || index >= description.length()) {
                return;
            }
            index = res + word.length();
            desc.incrementWage();
        }
    }
}
