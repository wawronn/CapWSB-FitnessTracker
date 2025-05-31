package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.Training;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
class TrainingMapper {

    @Autowired
    private final TrainingUserMapper trainingUserMapper;

    TrainingDto toDto(Training training) {
        return new TrainingDto(
                training.getId(),
                trainingUserMapper.toUserDto(training.getUser()),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }

    public Training toEntity(TrainingInputDto trainingDto) {
        return new Training(
        null,
            trainingDto.getStartTime(),
            trainingDto.getEndTime(),
            trainingDto.getActivityType(),
            trainingDto.getDistance(),
            trainingDto.getAverageSpeed()
        );
    }
}
