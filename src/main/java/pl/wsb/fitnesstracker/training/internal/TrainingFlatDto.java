package pl.wsb.fitnesstracker.training.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import pl.wsb.fitnesstracker.views.InOutView;

import java.util.Date;

class TrainingFlatDto {

    @JsonView(InOutView.Output.class)
    private final Long id;

    @JsonView(InOutView.Input.class)
    private final Long userId;

    @JsonView(InOutView.Input.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final Date startTime;

    @JsonView(InOutView.Input.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final Date endTime;

    @JsonView(InOutView.Input.class)
    private final ActivityType activityType;

    @JsonView(InOutView.Input.class)
    private final double distance;

    @JsonView(InOutView.Input.class)
    private final double averageSpeed;

    TrainingFlatDto(Long id, Long userId, Date startTime, Date endTime, ActivityType activityType, double distance, double averageSpeed) {
        this.id = id;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }

    public Long getId() {
        return id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public double getDistance() {
        return distance;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public Long getUserId() {
        return userId;
    }
}
