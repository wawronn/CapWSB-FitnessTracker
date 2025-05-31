package pl.wsb.fitnesstracker.training.internal;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import pl.wsb.fitnesstracker.training.api.TrainingProvider;
import pl.wsb.fitnesstracker.training.api.TrainingService;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserNotFoundException;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

// TODO: Provide Implementation and correct the return type of the method getTraining
@Component
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingProvider, TrainingService {

    private final TrainingRepository trainingRepository;
    private final UserProvider userProvider;

    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        return trainingRepository.findById(trainingId);
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getTrainingsByUserId(Long userId) {
        return trainingRepository.findById(userId).stream().toList();
    }

    @Override
    public List<Training> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findTrainingsByActivityType(activityType);
    }

    @Override
    public List<Training> getTrainingsCompletedAfterEndDate(Date endDate) {
        return trainingRepository.findAll()
                .stream()
                .filter(training -> training.getEndTime().after(endDate))
                .toList();
    }

    @Override
    public Training addTraining(Training training, Long userId) {
        User user = userProvider.getUser(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Training newTraining = new Training(
            user,
            training.getStartTime(),
            training.getEndTime(),
            training.getActivityType(),
            training.getDistance(),
            training.getAverageSpeed()
        );
        return trainingRepository.save(newTraining);
    }

    @Override
    public Training updateTraining(Training training, Long trainingId, Long userId) {
        Training trainingToUpdate = getTraining(trainingId).orElseThrow(() -> new TrainingNotFoundException(trainingId));
        User user = userProvider.getUser(userId).orElseThrow(() -> new UserNotFoundException(userId));
        trainingToUpdate.setUser(user);
        trainingToUpdate.setStartTime(training.getStartTime());
        trainingToUpdate.setEndTime(training.getEndTime());
        trainingToUpdate.setActivityType(training.getActivityType());
        trainingToUpdate.setDistance(training.getDistance());
        trainingToUpdate.setAverageSpeed(training.getAverageSpeed());
        return trainingRepository.save(trainingToUpdate);
    }
}
