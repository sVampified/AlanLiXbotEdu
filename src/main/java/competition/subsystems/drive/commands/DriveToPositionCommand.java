package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

import java.sql.SQLOutput;

public class DriveToPositionCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;

    double currentPosition;
    double goalPosition;
    double travelingDistance;
    double velocity;
    double lastPosition;


    @Inject
    public DriveToPositionCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    public void setTargetPosition(double position) {
        // This method will be called by the test, and will give you a goal distance.
        // You'll need to remember this target position and use it in your calculations.
        goalPosition = position;
    }

    @Override
    public void initialize() {
        // If you have some one-time setup, do it here.
        System.out.println(goalPosition);
        currentPosition = pose.getPosition();
        lastPosition = currentPosition;
    }

    @Override
    public void execute() {
        // Here you'll need to figure out a technique that:
        // - Gets the robot to move to the target position
        // - Hint: use pose.getPosition() to find out where you are
        // - Gets the robot stop (or at least be moving really really slowly) at the
        // target position

        // How you do this is up to you. If you get stuck, ask a mentor or student for
        // some hints!
        currentPosition = pose.getPosition();
        travelingDistance = goalPosition - currentPosition;
        velocity = currentPosition - lastPosition;
        if (travelingDistance < 0) {
            drive.tankDrive(-1, -1);
        } else if (travelingDistance > 0) {
            drive.tankDrive(1, 1);
        }


    }

    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
        if (travelingDistance == 0) {
             return true;
        }
        return false;
    }

}
