package org.usfirst.frc.team6843.robot.commands;

import org.usfirst.frc.team6843.robot.Robot;
import org.usfirst.frc.team6843.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class DriveTo extends Command {

  private final DriveSubsystem driveSubsystem;
  private final double targetInches;

  public DriveTo(double targetInches) {
    this.driveSubsystem = Robot.getInstance().getDriveSubsystem();
    requires(this.driveSubsystem);
    this.targetInches = targetInches;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.driveSubsystem.startDistance(this.targetInches);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double speed = this.driveSubsystem.getDistDriveRate();
    this.driveSubsystem.velocityDrive(speed, -speed);
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
