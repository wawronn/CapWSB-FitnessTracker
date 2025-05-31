package pl.wsb.fitnesstracker.training.api;

import pl.wsb.fitnesstracker.training.internal.TrainingInputDto;

public interface TrainingService {

    /**
     *
     * @param training informacje o treningu do utworzenia
     * @param userId ID użytkownika, który odbył dany trening
     * @return zwracana jest utworzona encja
     */

    Training addTraining(Training training, Long userId);

    Training updateTraining(Training training, Long trainingId, Long userId);
}
