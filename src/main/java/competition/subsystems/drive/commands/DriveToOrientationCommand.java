package competition.subsystems.drive.commands;

import javax.inject.Inject;

import competition.subsystems.pose.PoseSubsystem;
import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;

public class DriveToOrientationCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    double lastDegrees;
    double travelingDistance;
    double goalDegrees;
    double currentDegrees;
    double velocity;
    double goalRealHeading;

    @Inject
    public DriveToOrientationCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;

    }

    public void setTargetHeading(double heading) {
        // This method will be called by the test, and will give you a goal heading.
        // You'll need to remember this target position and use it in your calculations.
        goalDegrees = heading;
    }

    @Override
    public void initialize() {
        // If you have some one-time setup, do it here.
        goalRealHeading = goalDegrees;
    }

    @Override
    public void execute() {
        // Here you'll need to figure out a technique that:
        // - Gets the robot to turn to the target orientation
        // - Gets the robot stop (or at least be moving really really slowly) at the
        // target position






        currentDegrees = pose.getCurrentHeading().getDegrees();

        travelingDistance = goalDegrees - currentDegrees;
        velocity = currentDegrees - lastDegrees;
        double power = travelingDistance * 0.0397 - velocity * 1.999;
        drive.tankDrive(-power, power);
        lastDegrees = currentDegrees;



//        if (goalRealHeading > 180 || goalRealHeading < -180) {
//            goalDegrees -= 360;
//        }






        // How you do this is up to you. If you get stuck, ask a mentor or student for
        // some hints!
    }

    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
        travelingDistance = goalRealHeading - currentDegrees;
        velocity = currentDegrees - lastDegrees;
        if (Math.abs(travelingDistance) < 0.1 && Math.abs(velocity) < 0.1) {
            drive.tankDrive(0,0);
            return true;
        }
        return false;
    }
}
