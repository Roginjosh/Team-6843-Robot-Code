/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6843.robot.commands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;

/**
 * Designed to be used as a whileHeld on a button to approach the target AFTER a
 * rotate to target button is hit.
 * 
 * <p>
 * Note that {@link ResetRotatedToTarget} MUST be on release of the same button
 * as this command is a while held.
 * </p>
 */
public class ApproachTarget extends ConditionalCommand {
  /**
   * First runs rotate and then when that is done, runs drive.
   */
  public ApproachTarget() {
    super(new DriveToTarget(), new RotateToTarget());
  }

  /**
   * Returns false the first time to run the rotate. When the rotate finishes,
   * this return true until the {@link ResetRotatedToTarget} command runs. That
   * command MUST be on release of the same button that is held for this command.
   */
  public boolean condition() {
    return RotateToTarget.isTurnedToTarget();
  }
}
