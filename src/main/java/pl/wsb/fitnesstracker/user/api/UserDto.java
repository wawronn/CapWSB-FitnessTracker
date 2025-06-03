package pl.wsb.fitnesstracker.user.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Nullable;
import pl.wsb.fitnesstracker.views.InOutView;

import java.time.LocalDate;

public record UserDto(

        @JsonView(InOutView.Output.class)
        Long id,

        @JsonView(InOutView.Input.class)
        String firstName,

        @JsonView(InOutView.Input.class)
        String lastName,

        @JsonView(InOutView.Input.class)
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthdate,

        @JsonView(InOutView.Input.class)
        String email) {
}
