package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class TurnLeft90DegreesCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;

    @Inject
    public TurnLeft90DegreesCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double currentPosition = pose.getPosition();
        double degrees = pose.getCurrentHeading().getDegrees();
        double travelingDistance = degrees - currentPosition;
        double lastPosition;
        double velocity = currentPosition - lastPosition;
        double power = travelingDistance * .293828 - velocity * .1566;
        drive.tankDrive(power, power);
        lastPosition = currentPosition;

    }

}
