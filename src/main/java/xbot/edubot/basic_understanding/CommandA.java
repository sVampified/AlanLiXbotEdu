package xbot.edubot.basic_understanding;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;

public class CommandA extends BaseCommand {

    ExampleSubsystem exampleSubsystem;
    
    @Inject
    public CommandA(ExampleSubsystem exampleSubsystem) {
        addRequirements(exampleSubsystem);
        this.exampleSubsystem = exampleSubsystem;
    }
    
    @Override
    public void initialize() {
        log.info("Initializing!");
    }

    @Override
    public void execute() {
         exampleSubsystem.writeMessage("CommandA is the best.");
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            log.info("Something just interrupted CommandA! Oh noooooo... *dies*");
        } else {
            log.info("CommandA ending gracefully.");
        }
    }

}
