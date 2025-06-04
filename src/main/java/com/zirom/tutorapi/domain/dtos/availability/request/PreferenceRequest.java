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
public class PreferenceRequest {
    @NotNull(message = "Future days must be provided")
    @Min(value = 1, message = "Future days must be at least 1")
    @Max(value = 30, message = "Future days must be at most 30")
    private int futureDays;

    @NotNull
    @Min(value = 1, message = "Min notice hours must be at least 1")
    @Max(value = 240, message = "Min notice hours days must be at most 240")
    private double minNoticeHours;
}
