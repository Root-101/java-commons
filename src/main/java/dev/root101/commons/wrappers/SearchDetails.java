package dev.root101.clean.core.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.root101.clean.core.utils.validation.annotations.Sort;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.Map;

public class SearchDetails {

    @NotNull
    @PositiveOrZero
    @JsonProperty("page")
    private int page;

    @NotNull
    @Min(1)
    @JsonProperty("size")
    private int size;

    @JsonProperty("filter")
    Map<String, String> filter;

    @Sort
    @JsonProperty("sort")
    Map<String, String> sort;

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public Map<String, String> getFilter() {
        return filter;
    }

    public Map<String, String> getSort() {
        return sort;
    }

}
