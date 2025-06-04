package com.zirom.tutorapi.domain.dtos.availability.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DurationRequest {
    @NotNull
    @Min(value = 10, message = "Duration must be at least 10")
    @Max(value = 120, message = "Duration must be at most 120")
    private int duration;
}
