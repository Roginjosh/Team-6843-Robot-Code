/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6843.robot.commands;

import org.usfirst.frc.team6843.robot.Robot;
import org.usfirst.frc.team6843.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team6843.robot.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * The rotation is to the vision target angle at initialization. We rotate about
 * the center but the camera is a bit forward. This results in a turn that is a
 * bit wide of the target. However, that is what we want for the subsequent
 * repeated execution of DriveToTarget so as to have a better chance of squaring
 * up.
 */
public class RotateToTarget extends Command {
  // Used to distinquish between initial turn time and
  // drive time while approaching target.
  private static boolean turnedToTarget = false;

  private final DriveSubsystem driveSubsystem;
  private final VisionSubsystem visionSubsystem;
  private double newHeading;
  private static final int ON_TARGET_TARGET = 5;
  private int onTargetCount;

  public RotateToTarget() {
    super();
    this.driveSubsystem = Robot.getInstance().getDriveSubsystem();
    this.visionSubsystem = Robot.getInstance().getVisionSubsystem();
    // DO NOT require vision subsystem!
    requires(this.driveSubsystem);
  }

  /**
   * @return true if the initial rotate to command during approach has completed.
   */
  public static boolean isTurnedToTarget() {
    return RotateToTarget.turnedToTarget;
  }

  /**
   * @param turnedToTarget set to false by separate release command and set to
   *                       true in here upon completion.
   */
  public static void setTurnedToTarget(boolean turnedToTarget) {
    RotateToTarget.turnedToTarget = turnedToTarget;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (!visionSubsystem.isTargetDataGood()) {
      this.cancel();
      return;
    }
    final double targetAngle = this.visionSubsystem.getTargetAngle();
    final double lastCompletedRotateTo = RotateTo.getLastCompletedRotateTo();
    final double currentHeading = this.driveSubsystem.getGyroAngle();
    final double currentTargetWallHeading = currentHeading - lastCompletedRotateTo;
    // Rotate if targetAngle and currentTargetWallAngle are the same sign.
    boolean rotate = (targetAngle * currentTargetWallHeading) > 0.0;
    // The mixed sign cases are tougher...
    if (!rotate) {
      // We want to rotate if the magnitude of the target angle
      // is greater than the robot's wall angle. That is, if we
      // are widely pointed away from the target, rotate. Otherwise,
      // approach from where we are.
      final double diff = targetAngle + currentTargetWallHeading;
      // The above will be true if diff and targetAngle have the same sign.
      rotate = (targetAngle * diff) > 0.0;
    }
    // One more very unlikely case to consider.
    // rotate may still be false because the target angle was 0.0.
    // If the current wall angle is large, we should widen to get a better approach.
    double proposedDelta = targetAngle;
    if (!rotate && (targetAngle == 0.0)) {
      final double absCurrentTargetWallHeading = Math.abs(currentTargetWallHeading);
      rotate = (absCurrentTargetWallHeading > 10.0);
      if (rotate) {
        if (currentTargetWallHeading > 0.0) {
          proposedDelta = 5.0;
        } else {
          proposedDelta = -5.0;
        }
      }
    }
    this.newHeading = currentHeading;
    if (rotate) {
      if (proposedDelta > 0.0) {
        proposedDelta = Math.max(proposedDelta, 5.0);
      } else {
        proposedDelta = Math.min(proposedDelta, -5.0);
      }
      this.newHeading += proposedDelta;
    }
    double absDiff = this.driveSubsystem.startTurn(this.newHeading);
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
      }
    }
    return onTarget;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    this.driveSubsystem.endTurn();
    this.driveSubsystem.stop();
    RotateToTarget.setTurnedToTarget(true);
  }

  /**
   * Need this to be different from end to handle case of cancellation due to bad
   * vision data or button release before turn completion.
   */
  @Override
  protected void interrupted() {
    this.driveSubsystem.endTurn();
    this.driveSubsystem.stop();
  }
}
