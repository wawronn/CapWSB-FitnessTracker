package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.*;

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
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        final User newUser = userService.createUser(userMapper.toEntity(userDto));
        URI location = URI.create("/v1/users/" + newUser.getId());
        return ResponseEntity.created(location).body(userDto);
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
    public ResponseEntity<UserDto> updateUserById(@PathVariable Long userId, @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.updateUser(userId, user);
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
}