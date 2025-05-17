package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        final User newUser = userService.createUser(userMapper.toEntity(userDto));
        URI location = URI.create("/v1/users/" + newUser.getId());
        return ResponseEntity.created(location).body(userDto);
    }

    @GetMapping("/simple")
    public List<UserDtoSimple> getAllUsersSimple() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDtoSimple)
                .toList();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        return userService.getUser(userId)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUserById(@PathVariable Long userId, @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }

    @GetMapping("/email")
    public List<UserDtoByEmail> getUsersByEmail(@RequestParam String email) {
        return userService.findAllUsersByEmail(email)
                .stream()
                .map(userMapper::toDtoByEmail)
                .toList();
    }

    @GetMapping("/older/{time}")
    public List<UserDtoOlderThan> getUsersOlderThan(@PathVariable LocalDate time) {
        return userService.findAllUsersOlderThan(time)
                .stream()
                .map(userMapper::toDtoOlderThan)
                .toList();
    }
}