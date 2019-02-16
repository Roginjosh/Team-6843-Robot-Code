package org.usfirst.frc.team6843.robot.commands;

import org.usfirst.frc.team6843.robot.Robot;
import org.usfirst.frc.team6843.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drives to the provided distance. Useful for automated driving.
 */
public class DriveTo extends Command {

  private final DriveSubsystem driveSubsystem;
  private final double targetInches;
  private double startHeading;

  public DriveTo(double targetInches) {
    this.driveSubsystem = Robot.getInstance().getDriveSubsystem();
    requires(this.driveSubsystem);
    this.targetInches = targetInches;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.driveSubsystem.startDistance(this.targetInches);
    this.startHeading = driveSubsystem.getGyroAngle();
  }

  /**
   * Drives at PID speed but trys to correct based on gyro heading at
   * initialization and in here. It basically works but needs tuning for the new
   * drive base. If it starts to work really well, we may want to put this in
   * joystick drive for when there is only power input and no turn input.
   */
  @Override
  protected void execute() {
    double speed = this.driveSubsystem.getDistDriveRate();
    double currentHeading = this.driveSubsystem.getGyroAngle();
    double deltaAngle = currentHeading - this.startHeading;

    // Handle -180 and +180 noise.
    if (deltaAngle < -45.0) {
      deltaAngle = deltaAngle + 360.0;
    } else if (deltaAngle > 45.0) {
      deltaAngle = deltaAngle - 360.0;
    }
    // Silly value clamping
    if (Math.abs(deltaAngle) > 45.0) {
      deltaAngle = 0.0;
    }
    double p = deltaAngle < 0.0 ? 0.05 : 0.025; // TODO check on new bot
    double turnRate = p * deltaAngle;
    double turnFactor = speed * turnRate;
    this.driveSubsystem.velocityDrive(speed - turnFactor, -(speed + turnFactor));
    // this.driveSubsystem.velocityDrive(speed, -speed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return this.driveSubsystem.isDistOnTarget();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    this.driveSubsystem.endDistance();
    this.driveSubsystem.stop();
  }

}
