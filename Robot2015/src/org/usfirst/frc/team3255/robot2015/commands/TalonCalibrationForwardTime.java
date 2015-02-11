package org.usfirst.frc.team3255.robot2015.commands;

/**
 *
 */
public class TalonCalibrationForwardTime extends CommandBase {
	
	double timeOut;

    public TalonCalibrationForwardTime(double seconds) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	timeOut = seconds;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
      	this.setTimeout(timeOut);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drivetrain.calibrateTalon(1.0);
    	drivetrain.setStrafeSpeed(1.0);
    	cassette.setSpeed(1.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
