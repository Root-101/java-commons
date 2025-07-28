package dev.root101.commons.examples;

import dev.root101.commons.examples.Status_Enum.StatusResponse;
import dev.root101.commons.utils.EnumMappeable;
import dev.root101.commons.utils.EnumMappeableService;

public class EnumMappeableMain {

    public static void main(String[] args) throws Exception {
        System.out.println(EnumMappeableService.map(Status_Enum.class));
        
        System.out.println(EnumMappeableService.map(Status_Enum.class, Status_Enum::getName));
    }
}

enum Status_Enum implements EnumMappeable<StatusResponse> {
    ACTIVE("Active"),
    ARCHIVED("Archived");

    record StatusResponse(
            String name,
            boolean active) {

    }

    private final String name;

    private Status_Enum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public StatusResponse map() {
        return new StatusResponse(
                getName(),
                this == Status_Enum.ACTIVE
        );
    }

    @Override
    public String toString() {
        return getName();
    }

}
