package org.usfirst.frc.team3255.robot2015.subsystems;

import org.usfirst.frc.team3255.robot2015.OI;
import org.usfirst.frc.team3255.robot2015.RobotMap;
import org.usfirst.frc.team3255.robot2015.commands.DriveArcade;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drivetrain extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	//Motor Controllers
	Talon leftFrontTalon = null;
	Talon leftBackTalon = null;
	Talon rightFrontTalon = null;
	Talon rightBackTalon = null;
	Talon hRightTalon = null;
	Talon hLeftTalon = null;
	
	// Solenoids
	DoubleSolenoid strafeSolenoid = null;
	
	// Robot Drive
	RobotDrive robotDrive = null;

	public Gyro gyro = null;
	
    public Drivetrain() {
		super();
		
		init();
	}

	public Drivetrain(String name) {
		super(name);
		
		init();
	}

	public void init() {
		leftFrontTalon = new Talon(RobotMap.DRIVETRAIN_FRONT_LEFT_TALON);
		leftBackTalon = new Talon(RobotMap.DRIVETRAIN_BACK_LEFT_TALON);
		rightFrontTalon = new Talon(RobotMap.DRIVETRAIN_FRONT_RIGHT_TALON);
		rightBackTalon = new Talon(RobotMap.DRIVETRAIN_BACK_RIGHT_TALON);
		hRightTalon = new Talon(RobotMap.DRIVETRAIN_H_RIGHT_TALON);
		hLeftTalon = new Talon(RobotMap.DRIVETRAIN_H_LEFT_TALON);
		
		strafeSolenoid = new DoubleSolenoid(RobotMap.DRIVETRAIN_SOLENOID_OPEN, RobotMap.DRIVETRAIN_SOLENOID_CLOSE);
		
		robotDrive = new RobotDrive(leftFrontTalon, leftBackTalon, rightFrontTalon, rightBackTalon);
		
		gyro = new Gyro(RobotMap.DRIVETRAIN_GYRO);
		
		this.strafeDisable();
	}
	
	public void setSpeed(double s) {
		leftFrontTalon.set(s);
		leftBackTalon.set(s);
		rightFrontTalon.set(-s);
		rightBackTalon.set(-s);
	}

	public void tankDrive() {
		robotDrive.tankDrive(OI.driverStick.getRawAxis(RobotMap.AXIS_TANK_LEFT),
				OI.driverStick.getRawAxis(RobotMap.AXIS_TANK_RIGHT));
	}
	
	public void arcadeDrive() {
		// negate the drive axis so that pushing stick forward is +1
		double moveSpeed = -OI.driverStick.getRawAxis(RobotMap.AXIS_ARCADE_MOVE);
		double rotateSpeed = OI.driverStick.getRawAxis(RobotMap.AXIS_ARCADE_ROTATE);
		robotDrive.arcadeDrive(moveSpeed, rotateSpeed);
		
		if(strafeSolenoid.get() == DoubleSolenoid.Value.kForward) {
			double hSpeed = OI.driverStick.getRawAxis(RobotMap.AXIS_HDRIVE);
			hLeftTalon.set(hSpeed);
			hRightTalon.set(hSpeed);
		}
		else {
			hLeftTalon.set(0.0);
			hRightTalon.set(0.0);
		}
	}
	
	public void strafeEnable() {
		strafeSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public void strafeDisable() {
		strafeSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public double getSpeed() {
		return leftFrontTalon.get();
	}

	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new DriveArcade());
    }

	public void resetGyro() {
		gyro.reset();
	}

	public double getGyro() {
		return gyro.getAngle();
	}
}

