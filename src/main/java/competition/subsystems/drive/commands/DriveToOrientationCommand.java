package competition.subsystems.drive.commands;

import javax.inject.Inject;

import competition.subsystems.pose.PoseSubsystem;
import edu.wpi.first.math.geometry.Rotation2d;
import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import xbot.common.math.PID;
import xbot.common.math.PIDManager;
import xbot.common.subsystems.drive.control_logic.HeadingAssistModule;
import xbot.common.subsystems.drive.control_logic.HeadingModule;

public class DriveToOrientationCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    PIDManager pid;
    HeadingModule headingModule;
    double lastDegrees;
    double travelingDistance;
    double goalDegrees;
    double currentDegrees;
    double velocity;
    double goalRealHeading;
    double startingAngle;

    @Inject
    public DriveToOrientationCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose, PIDManager.PIDManagerFactory pidManagerFactory, HeadingModule.HeadingModuleFactory headingModuleFactory) {
        this.drive = driveSubsystem;
        this.pose = pose;
        pid = pidManagerFactory.create("Rotate");
        pid.setEnableErrorThreshold(true);
        pid.setErrorThreshold(0.1);
        pid.setEnableDerivativeThreshold(true);
        pid.setDerivativeThreshold(0.1);

        pid.setP(0.0397);
        pid.setD(1.999);
        headingModule = headingModuleFactory.create(pid);
    }

    public void setTargetHeading(double heading) {
        // This method will be called by the test, and will give you a goal heading.
        // You'll need to remember this target position and use it in your calculations.
        goalDegrees = heading;
    }

    @Override
    public void initialize() {
        // If you have some one-time setup, do it here.

//        startingAngle = pose.getCurrentHeading().getDegrees();
//        goalRealHeading = goalDegrees - startingAngle;
        goalRealHeading = goalDegrees;
    }

    @Override

    public void execute() {
        // Here you'll need to figure out a technique that:
        // - Gets the robot to turn to the target orientation
        // - Gets the robot stop (or at least be moving really really slowly) at the
        // target position

//        currentDegrees = pose.getCurrentHeading().getDegrees() - startingAngle;
//        System.out.println("This is starting angle " + startingAngle);
//
//        if (pose.getCurrentHeading().getDegrees() < 0 && startingAngle < 0) {
//            currentDegrees = 360 + pose.getCurrentHeading().getDegrees() - startingAngle;
//
////            if (pose.getCurrentHeading().getDegrees() > 0 && startingAngle < 0) {
////                currentDegrees = pose.getCurrentHeading().getDegrees() - currentDegrees;
////            }
//        }
//
//        System.out.println("This is the goal heading " + goalRealHeading);
//        System.out.println("This is the current degrees " + currentDegrees);
//        System.out.println("This is actual current degrees " + pose.getCurrentHeading().getDegrees());
//        System.out.println("Distance froms starting angle " + (goalRealHeading + startingAngle));
//        travelingDistance = goalRealHeading - currentDegrees;
//        velocity = currentDegrees - lastDegrees;
//        double power = travelingDistance * 0.0397 - velocity * 1.999;
//        drive.tankDrive(-power, power);
//        lastDegrees = currentDegrees;

//






        // How you do this is up to you. If you get stuck, ask a mentor or student for
        // some hints!

        double power = headingModule.calculateHeadingPower(goalRealHeading);
        drive.tankDrive(-power, power);
    }

    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
//        travelingDistance = goalRealHeading - currentDegrees;
//        velocity = currentDegrees - lastDegrees;
//        if (Math.abs(travelingDistance) < 0.01 && Math.abs(velocity) < 0.01) {
//            drive.tankDrive(0,0);
//            return true;
//        }
        return pid.isOnTarget();
    }
}
