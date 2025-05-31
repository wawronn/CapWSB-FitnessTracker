package pl.wsb.fitnesstracker.training.api;

import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrainingProvider {

    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    Optional<Training> getTraining(Long trainingId);

    /**
     * Zwraca listę wszystkich treningów
     *
     * @return lista kreningów
     */
    List<Training> getAllTrainings();

    /**
     * Zwraca listę treningów użytkownika
     *
     * @param userId ID użytkownika
     * @return lista treningów
     */
    List<Training> getTrainingsByUserId(Long userId);

    /**
     *
     * @param activityType typ aktywności
     * @return lista treningó z danym typem aktywności
     */

    List<Training> getTrainingsByActivityType(ActivityType activityType);

    /**
     *
     * @param endDate Data końca treningu
     * @return Zwraca treningi zakończone po danej dacie
     */
    List<Training> getTrainingsCompletedAfterEndDate(Date endDate);

}
