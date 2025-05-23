package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

public record UserDtoByEmail( @Nullable Long id, String email ) {
}
