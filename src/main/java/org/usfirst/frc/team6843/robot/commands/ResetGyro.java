package org.usfirst.frc.team6843.robot.commands;

import org.usfirst.frc.team6843.robot.Robot;
import org.usfirst.frc.team6843.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Kill all other running commands.
 */
public class ResetGyro extends Command {
  private final DriveSubsystem driveSubsystem;

  public ResetGyro() {
    this.driveSubsystem = Robot.getInstance().getDriveSubsystem();
    requires(this.driveSubsystem);
  }

  /**
   * Remove all running commands from the scheduler.
   */
  @Override
  protected void initialize() {
    this.driveSubsystem.resetGyro();
  }

  /**
   * Done right away.
   */
  @Override
  protected boolean isFinished() {
    return true;
  }
}
