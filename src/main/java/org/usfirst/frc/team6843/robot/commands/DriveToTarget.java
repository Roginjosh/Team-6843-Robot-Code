package org.usfirst.frc.team6843.robot.commands;

import org.usfirst.frc.team6843.robot.Robot;
import org.usfirst.frc.team6843.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team6843.robot.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class DriveToTarget extends Command {

  private final DriveSubsystem driveSubsystem;
  private final VisionSubsystem visionSubsystem;
  private double targetInches;

  public DriveToTarget() {
    this.driveSubsystem = Robot.getInstance().getDriveSubsystem();
    this.visionSubsystem = Robot.getInstance().getVisionSubsystem();
    // DO NOT require vision subsystem!
    requires(this.driveSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.targetInches = this.visionSubsystem.getTargetInchesDistance();
    this.targetInches = this.targetInches > 0.0 ? this.targetInches : 12.0;
    if (this.targetInches > 18.0) {
      this.driveSubsystem.startSlowDistance(this.targetInches);
    } else {
      this.driveSubsystem.startDistance(this.targetInches);
    }
    this.setTimeout(1.0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double speed = this.driveSubsystem.getDistDriveRate();
    double targetAngle = this.visionSubsystem.getTargetAngle();
    double p = targetAngle < 0.0 ? 0.02 : 0.01;
    double turnRate = p * targetAngle;
    double turnFactor = speed * turnRate;
    this.driveSubsystem.velocityDrive(speed + turnFactor, -(speed - turnFactor));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return this.driveSubsystem.isDistOnTarget() || this.isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    this.driveSubsystem.endDistance();
    this.driveSubsystem.stop();
  }

}
