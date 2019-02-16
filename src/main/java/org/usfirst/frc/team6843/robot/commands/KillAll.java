package org.usfirst.frc.team6843.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * Kill all other running commands. Useful for getting out of
 * trouble with run away commands. Use with care.
 */
public class KillAll extends Command {
  /**
   * Remove all running commands from the scheduler.
   */
  @Override
  protected void initialize() {
    Scheduler.getInstance().removeAll();
  }

  /**
   * Done right away.
   */
  @Override
  protected boolean isFinished() {
    return true;
  }
}
