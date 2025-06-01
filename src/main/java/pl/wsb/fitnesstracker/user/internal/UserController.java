package pl.wsb.fitnesstracker.user.internal;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.errors.ErrorDto;
import pl.wsb.fitnesstracker.user.api.*;
import pl.wsb.fitnesstracker.views.InOutView;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

/**
 * Serwis do zarządzania użytkownikami
 */

@RestController
@RequestMapping(value = "/v1/users", produces = "application/json")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    /**
     * zwraca wszystkich użytkowników
     *
     * @return lista użytkowników
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * zakłada nowego użytkownika
     *
     * @param userDto dane użytkownika
     * @return ResponseEntity z userDto
     */

    @PostMapping
    @JsonView(InOutView.Output.class)
    public ResponseEntity<UserDto> addUser(@RequestBody @JsonView({InOutView.Input.class}) UserDto userDto) {
        final User newUser = userService.createUser(userMapper.toEntity(userDto));
        URI location = URI.create("/v1/users/" + newUser.getId());
        return ResponseEntity.created(location).body(userMapper.toDto(newUser));
    }

    /**
     * zwraca podstawowe informacje o wszystkich użytkownikach
     *
     * @return lista podstawowych informacji
     */

    @GetMapping("/simple")
    public List<UserDtoSimple> getAllUsersSimple() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDtoSimple)
                .toList();
    }

    /**
     * zwraca użytkownika po jego ID
     *
     * @param userId ID użytkownika
     * @return RespnseEntity użytkownika
     * @exception UserNotFoundException
     */

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        return userService.getUser(userId)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    /**
     * usuwa użytkownika
     *
     * @param userId ID użytkownika
     * @return ResponseEntity noContent
     */

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * aktualizacja danych użytkownika
     *
     * @param userId ID użytkownika
     * @param userDto informacje o użytkowniku
     * @return ResponseEntity ok
     */

    @PutMapping("/{userId}")
    @JsonView(InOutView.Output.class)
    public ResponseEntity<UserDto> updateUserById(@PathVariable Long userId, @RequestBody @JsonView({InOutView.Input.class}) UserDto userDto) {
        User updatedUser = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }

    /**
     * zwraca użytkowników po adresie e-mail
     *
     * @param email adres e-mail (lub jego fragment)
     * @return lista użytkowników
     */

    @GetMapping("/email")
    public List<UserDtoByEmail> getUsersByEmail(@RequestParam String email) {
        return userService.findAllUsersByEmail(email)
                .stream()
                .map(userMapper::toDtoByEmail)
                .toList();
    }

    /**
     * zwraca użytkowników urodzonych wcześniej niż podana data
     *
     * @param time data w formacie YYYY-MM-DD
     * @return lista użytkowników
     */

    @GetMapping("/older/{time}")
    public List<UserDtoOlderThan> getUsersOlderThan(@PathVariable LocalDate time) {
        return userService.findAllUsersOlderThan(time)
                .stream()
                .map(userMapper::toDtoOlderThan)
                .toList();
    }

    /**
     *
     * Obsługa błędu "DataIntegrityViolation" - naruszenie klucza głównego (powtórzony adres e-mail)
     *
     * @param e - wyjątek
     * @return ErrorDto informacja o błędize
     */

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDto> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.internalServerError().body(new ErrorDto("Użytkownik z takim adresem e-mail już istnieje"));
    }
}