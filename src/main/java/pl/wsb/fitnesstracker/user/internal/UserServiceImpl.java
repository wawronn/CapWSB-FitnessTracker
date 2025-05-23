package pl.wsb.fitnesstracker.user.internal;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.*;
import pl.wsb.fitnesstracker.user.api.UserDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long userId) {
        User userToDelete = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(userToDelete);
    }

    @Override
    public User updateUser(Long userId, User user) {
        try {
            User userToUpdate = userRepository.getReferenceById(userId);
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setBirthdate(user.getBirthdate());
            userToUpdate.setEmail(user.getEmail());
            userRepository.save(userToUpdate);
            return userToUpdate;
        } catch (EntityNotFoundException e) {
            throw new UserNotFoundException(userId);
        }
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllUsersByEmail(String email) {
        return userRepository.findUsersByEmail(email);
    }

    @Override
    public List<User> findAllUsersOlderThan(LocalDate birthdate) {
        return userRepository.findUsersOlderThan(birthdate);
    }

}