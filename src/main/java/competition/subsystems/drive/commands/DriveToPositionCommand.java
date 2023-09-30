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
        double power = travelingDistance * .299828 - velocity * 1.899;
        drive.tankDrive(power, power);
        lastPosition = currentPosition;





        /*if (travelingDistance > 0) {
            drive.tankDrive(1, 1);
            if (travelingDistance <= 2 && velocity > 2.5) {
                drive.tankDrive(-1,-1);
            }
        } else {
                drive.tankDrive(-1, -1);
        */
    }

        /*if(velocity < 2.7 && velocity > 2.5) {
            drive.tankDrive(-0.5, -0.5);
        }
        */

    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
        velocity = currentPosition - lastPosition;
        travelingDistance = goalPosition - currentPosition;
        if (Math.abs(travelingDistance) < 0.1 && Math.abs(velocity) < 0.01) {
            return true;
        }
        return false;
    }

}
