package org.usfirst.frc.team6843.robot.commands;

import org.usfirst.frc.team6843.robot.Robot;
import org.usfirst.frc.team6843.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Point the robot upfield and run this command to reset the yaw. Useful if bad
 * drift is witeness during a match or while running a long time during testing.
 * For competition, this should be run using a
 * {@link org.usfirst.frc.team6843.robot.triggers.TwoButtonTrigger} with one
 * button from each controller.
 */
public class ResetGyro extends Command {
  private final DriveSubsystem driveSubsystem;

  public ResetGyro() {
    this.driveSubsystem = Robot.getInstance().getDriveSubsystem();
    requires(this.driveSubsystem);
  }

  /**
   * Reset the gyro yaw and orientation to forward.
   */
  @Override
  protected void initialize() {
    this.driveSubsystem.resetGyro();
    Robot.getInstance().updateStartingHeading(Robot.StartOrientation.FORWARD);
  }

  /**
   * Done right away.
   */
  @Override
  protected boolean isFinished() {
    return true;
  }
}
