package pl.wsb.fitnesstracker.training.internal;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.views.InOutView;

import java.util.Date;

class TrainingDto {

        @JsonView(InOutView.Output.class)
        private final Long id;

        @JsonView(InOutView.Input.class)
        private final UserDto user;

        @JsonView(InOutView.Input.class)
        private final Date startTime;

        @JsonView(InOutView.Input.class)
        private final Date endTime;

        @JsonView(InOutView.Input.class)
        private final ActivityType activityType;

        @JsonView(InOutView.Input.class)
        private final double distance;

        @JsonView(InOutView.Input.class)
        private final double averageSpeed;

        TrainingDto(Long id, UserDto user, Date startTime, Date endTime, ActivityType activityType, double distance, double averageSpeed) {
                this.id = id;
                this.user = user;
                this.startTime = startTime;
                this.endTime = endTime;
                this.activityType = activityType;
                this.distance = distance;
                this.averageSpeed = averageSpeed;
        }

        public Long getId() {
                return id;
        }

        public UserDto getUser() {
                return user;
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
}