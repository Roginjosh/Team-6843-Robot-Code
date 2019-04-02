/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6843.robot.commands;

import org.usfirst.frc.team6843.robot.Robot;
import org.usfirst.frc.team6843.robot.subsystems.HatchPanelSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class SmartResetHatchEncoder extends Command {
  HatchPanelSubsystem hatchPanelSubsystem;
  public SmartResetHatchEncoder() {
    this.hatchPanelSubsystem = Robot.getInstance().getHatchPanelSubsystem();
    requires(this.hatchPanelSubsystem);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    this.hatchPanelSubsystem.smartResetHatchEncoder();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (this.hatchPanelSubsystem.getRightHatchLimit()){
      return true;
    } else {
      return false;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    this.hatchPanelSubsystem.clearLinearDistance();
    this.hatchPanelSubsystem.manipulateOffsetVariable(3.625);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
