package org.usfirst.frc.team6843.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * To be on release of same button that runs the {@link ApproachTarget} command
 * whild held.
 */
public class ResetRotatedToTarget extends Command {
  /**
   * Indicate that the rotation is needed again from next approach.
   */
  @Override
  protected void initialize() {
    RotateToTarget.setTurnedToTarget(false);
  }

  /**
   * Done right away.
   */
  @Override
  protected boolean isFinished() {
    return true;
  }
}
