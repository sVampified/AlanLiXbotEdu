package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.math.PIDManager;


import java.sql.SQLOutput;

public class DriveToPositionCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;

    PIDManager pid;

    double currentPosition;
    double goalPosition;
    double travelingDistance;
    double velocity;
    double lastPosition;


    @Inject
    public DriveToPositionCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose, PIDManager.PIDManagerFactory pidManagerFactory) {
        this.drive = driveSubsystem;
        this.pose = pose;
        this.pid = pidManagerFactory.create("DriveToPoint");
        pid.setEnableErrorThreshold(true); // This is true, which means turning on error (distance checking)
        pid.setErrorThreshold(0.1);
        pid.setEnableDerivativeThreshold(true); //This is true, which means turning on derivative (speed checking)
        pid.setDerivativeThreshold(0.1);

        //Manually adjust values for action
        pid.setP(.599828);
        pid.setD(1.999);
    }

    public void setTargetPosition(double position) {
        // This method will be called by the test, and will give you a goal distance.
        // You'll need to remember this target position and use it in your calculations.
        goalPosition = position;
    }

    @Override
    public void initialize() {
        // If you have some one-time setup, do it here.
        pid.reset();

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
//        currentPosition = pose.getPosition();
//        travelingDistance = goalPosition - currentPosition;
//        velocity = currentPosition - lastPosition;
//        double power = travelingDistance * .399828 - velocity * 1.799;
//        drive.tankDrive(power, power);
//        lastPosition = currentPosition;





        /*if (travelingDistance > 0) {
            drive.tankDrive(1, 1);
            if (travelingDistance <= 2 && velocity > 2.5) {
                drive.tankDrive(-1,-1);
            }
        } else {
                drive.tankDrive(-1, -1);
        */
        double currentPosition = pose.getPosition();
        double power = pid.calculate(goalPosition, currentPosition);
        drive.tankDrive(power, power);
    }

        /*if(velocity < 2.7 && velocity > 2.5) {
            drive.tankDrive(-0.5, -0.5);
        }
        */

    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
//        velocity = currentPosition - lastPosition;
//        travelingDistance = goalPosition - currentPosition;
//        if (Math.abs(travelingDistance) < 0.1 && Math.abs(velocity) < 0.01) {
//            return true;
//        }
        return pid.isOnTarget();
    }

}
