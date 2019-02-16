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

/**
 * Rotates the robot in place to the target angle. There will be many instances
 * of this command mapped to different buttons, one for each interesting scoring
 * angle (there are 8 of them). One of these MUST be run before
 * {@link ApproachTarget}.
 */
public class RotateTo extends Command {
  // Used to know last manual rotate to heading
  // when on target approach.
  private static double lastCompletedRotateTo = 0.0;

  private final DriveSubsystem driveSubsystem;
  private final double targetHeading;
  private static final int ON_TARGET_TARGET = 5;
  private int onTargetCount;

  public RotateTo(double targetHeading) {
    super();
    this.driveSubsystem = Robot.getInstance().getDriveSubsystem();
    requires(this.driveSubsystem);
    this.targetHeading = targetHeading;
  }

  /**
   * @return the last reported completed rotate to heading.
   */
  public static double getLastCompletedRotateTo() {
    return RotateTo.lastCompletedRotateTo;
  }

  /**
   * @param lastCompletedRotateTo the latest completed rotate to target.
   */
  private static void setLastCompletedRotateTo(double lastCompletedRotateTo) {
    RotateTo.lastCompletedRotateTo = lastCompletedRotateTo;
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
    this.driveSubsystem.velocityDrive(speed, speed);
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
      } else {
        RotateTo.setLastCompletedRotateTo(this.targetHeading);
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
