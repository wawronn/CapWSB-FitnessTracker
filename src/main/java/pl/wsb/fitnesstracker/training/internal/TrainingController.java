package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.training.api.Training;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/trainings", produces = "application/json")
@RequiredArgsConstructor
class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;


    @GetMapping
    List<TrainingDto> getAllTrainings() {
        return trainingService.getAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @PostMapping
    ResponseEntity<TrainingDto> postTraining(@RequestBody TrainingInputDto trainingDto) {
        Training newTraining = trainingService.addTraining(trainingMapper.toEntity(trainingDto), trainingDto.getUserId());
        TrainingDto newTrainingDto =  trainingMapper.toDto(newTraining);
        URI location = URI.create("/v1/trainings/" + newTrainingDto.getId());
        return ResponseEntity.created(location).body(newTrainingDto);
    }

    @PutMapping("/{trainingId}")
    ResponseEntity<TrainingDto> putTraining(@PathVariable Long trainingId, @RequestBody TrainingInputDto trainingDto) {
        Training updatedTraining = trainingService.updateTraining(trainingMapper.toEntity(trainingDto), trainingId, trainingDto.getUserId());
        return ResponseEntity.ok(trainingMapper.toDto(updatedTraining));
    }

    @GetMapping("/user/{userId}")
    List<TrainingDto> getTrainingByUserId(@PathVariable Long userId) {
        return  trainingService.getTrainingsByUserId(userId)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/activityType")
    List<TrainingDto> getTrainingsByActivityType(@RequestParam ActivityType activityType) {
        return trainingService.getTrainingsByActivityType(activityType)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/finished/{afterTime}")
    List<TrainingDto> getTrainingsByAfterTime(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date afterTime) {
        return trainingService.getTrainingsCompletedAfterEndDate(afterTime)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

}
