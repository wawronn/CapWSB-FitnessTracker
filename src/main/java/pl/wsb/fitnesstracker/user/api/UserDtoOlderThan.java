package pl.wsb.fitnesstracker.user.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record UserDtoOlderThan(@Nullable Long Id, String firstName, String lastName,
                               @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate ) {
}
