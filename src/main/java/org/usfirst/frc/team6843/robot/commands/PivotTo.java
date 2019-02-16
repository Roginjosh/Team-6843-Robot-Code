/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6843.robot.commands;

import org.usfirst.frc.team6843.robot.Robot;
import org.usfirst.frc.team6843.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class PivotTo extends Command {

  private final DriveSubsystem driveSubsystem;
  private final double targetHeading;
  private static final int ON_TARGET_TARGET = 5;
  private int onTargetCount;

  public PivotTo(double targetHeading) {
    super();
    this.driveSubsystem = Robot.getInstance().getDriveSubsystem();
    requires(this.driveSubsystem);
    this.targetHeading = targetHeading;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    double absDiff = this.driveSubsystem.startTurn(this.targetHeading);
    this.onTargetCount = 0;
    this.setTimeout((absDiff / 90.0) + 0.25);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double speed = DriveSubsystem.ROTATE_VELOCITY_BASE * this.driveSubsystem.getGyroTurnRate();
    if (speed > 0.0) {
      this.driveSubsystem.velocityDrive(speed, 0.0);
    } else {
      this.driveSubsystem.velocityDrive(0.0, speed);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (this.isTimedOut()) {
      return true;
    }
    boolean onTarget = this.driveSubsystem.isTurnOnTarget();
    if (!onTarget) {
      this.onTargetCount = 0;
    } else {
      this.onTargetCount++;
      if (this.onTargetCount < ON_TARGET_TARGET) {
        onTarget = false;
      }
    }
    return onTarget;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    this.driveSubsystem.endTurn();
    this.driveSubsystem.stop();
  }

}
