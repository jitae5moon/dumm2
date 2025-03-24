package org.project.articleservice.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = PaginationService.class)
class PaginationServiceTest {

    PaginationService sut;

    @Autowired
    public PaginationServiceTest(PaginationService sut) {
        this.sut = sut;
    }

    @MethodSource
    @ParameterizedTest
    public void testCalculatingPaginationBarNumbers(int currentPage, int totalPages, List<Integer> result) {
        List<Integer> paginationBarNumbers = sut.getPaginationBarNumbers(currentPage, totalPages);

        assertThat(paginationBarNumbers).isEqualTo(result);
    }

    private static Stream<Arguments> testCalculatingPaginationBarNumbers() {
        return Stream.of(
                Arguments.of(1, 10, List.of(1, 2, 3, 4, 5)),
                Arguments.of(2, 10, List.of(1, 2, 3, 4, 5)),
                Arguments.of(3, 10, List.of(1, 2, 3, 4, 5)),
                Arguments.of(4, 10, List.of(2, 3, 4, 5, 6)),
                Arguments.of(5, 10, List.of(3, 4, 5, 6, 7)),
                Arguments.of(6, 10, List.of(4, 5, 6, 7, 8)),
                Arguments.of(7, 10, List.of(5, 6, 7, 8, 9)),
                Arguments.of(8, 10, List.of(6, 7, 8, 9, 10)),
                Arguments.of(9, 10, List.of(6, 7, 8, 9, 10)),
                Arguments.of(10, 10, List.of(6, 7, 8, 9, 10))
        );
    }
}