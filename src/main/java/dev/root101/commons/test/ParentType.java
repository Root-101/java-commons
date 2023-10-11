/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.root101.clean.core.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.root101.clean.core.exceptions.NotFoundException;
import dev.root101.clean.core.test.ParentType.PlatformResponse;
import dev.root101.clean.core.utils.EnumMappeable;
import jakarta.validation.constraints.Size;

/**
 *
 * @author Yo
 */
public enum ParentType implements EnumMappeable<PlatformResponse> {
    ACTIVE(1, "Active"),
    ON_HOLD(2, "on Hold"),
    CLOSED(3, "Closed");

    public record PlatformResponse(
            @JsonProperty("id")
            int id,
            @JsonProperty("name")
            String name) {

    }
    private final int id;

    @Size(max = 3)
    private final String name;

    private ParentType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public PlatformResponse map() {
        return new PlatformResponse(id, name);
    }

    @Override
    public String toString() {
        return getName();
    }

    public static ParentType resolve(int id) {
        for (ParentType value : ParentType.values()) {
            if (value.getId() == id) {
                return value;
            }
        }
        throw new NotFoundException("No Parent Type found with id '%s'.".formatted(id));
    }
}
