package org.project.articleservice.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PaginationService {

    private static final Integer BAR_SIZE = 5;

    public List<Integer> getPaginationBarNumbers(int currentPage, int totalPages) {
        int endPage = (int) (Math.ceil((double) currentPage / BAR_SIZE) * BAR_SIZE);
        if (endPage > totalPages) endPage = totalPages;
        int startPage = endPage - BAR_SIZE + 1;

        return IntStream.range(startPage, endPage + 1).boxed().collect(Collectors.toList());
    }

}
