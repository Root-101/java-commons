package dev.root101.commons.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.springframework.data.domain.Page;

public class SearchResponse<T> {

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_elements")
    private long totalElements;

    @JsonProperty("data")
    private List<T> data;

    public static <T> SearchResponse<T> build(Page<T> page) {
        return new SearchResponse<>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getContent()
        );
    }

    private SearchResponse(int totalPages, long totalElements, List<T> data) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.data = data;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public List<T> getData() {
        return data;
    }

}
