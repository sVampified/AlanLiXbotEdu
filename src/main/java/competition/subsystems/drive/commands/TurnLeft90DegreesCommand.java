package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class TurnLeft90DegreesCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    double lastDegrees;
    double travelingDistance;
    double goalDegrees;
    double currentDegrees;
    double velocity;

    @Inject
    public TurnLeft90DegreesCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    @Override
    public void initialize() {
        currentDegrees = pose.getCurrentHeading().getDegrees();
        goalDegrees = currentDegrees + 90;
    }

    @Override
    public void execute() {


        currentDegrees = pose.getCurrentHeading().getDegrees();
        travelingDistance = goalDegrees - currentDegrees;
        velocity = currentDegrees - lastDegrees;
        double power = travelingDistance * 0.0397 - velocity * 1.999;
        drive.tankDrive(-power, power);

        lastDegrees = currentDegrees;


    }


    // NOTEEEEEE, RANGE OF ROTATION IS FROM -180 to 180, so 240 degrees should be -120 degrees
    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
        travelingDistance = goalDegrees - currentDegrees;
        velocity = currentDegrees - lastDegrees;
       if (Math.abs(travelingDistance) < 0.1 && Math.abs(velocity) < 0.1) {
           drive.tankDrive(0,0);
           return true;
       }
        return false;
    }

}
