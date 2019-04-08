package org.usfirst.frc.team6843.robot.commands;

import org.usfirst.frc.team6843.robot.Robot;
import org.usfirst.frc.team6843.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drives to the provided distance. Useful for automated driving.
 */
public class DriveDrivebase extends Command {

  private final DriveSubsystem driveSubsystem;
  private final double time;
  private final double speed;
  private final double curve;


  public DriveDrivebase(double time, double speed, double curve) {
    this.driveSubsystem = Robot.getInstance().getDriveSubsystem();
    requires(this.driveSubsystem);
    this.time = time;
    this.speed = speed;
    this.curve = curve;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    setTimeout(time);
  }

  /**
   * Drives at PID speed but trys to correct based on gyro heading at
   * initialization and in here. It basically works but needs tuning for the new
   * drive base. If it starts to work really well, we may want to put this in
   * joystick drive for when there is only power input and no turn input.
   */
  @Override
  protected void execute() {
    this.driveSubsystem.arcadeDrive(speed, curve);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    this.driveSubsystem.arcadeDrive(0, 0);
  }

}
