package dev.root101.clean.core.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
