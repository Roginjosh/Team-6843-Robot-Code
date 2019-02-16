/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6843.robot.commands;

import org.usfirst.frc.team6843.robot.subsystems.ClimbingSubsystem;
import org.usfirst.frc.team6843.robot.subsystems.HatchPanelSubsystem;
import org.usfirst.frc.team6843.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class PneumaticsOff extends Command {
  protected ClimbingSubsystem ClimbingSubsystem;
  protected HatchPanelSubsystem HatchPanelSubsystem;
  public PneumaticsOff() {
    this.ClimbingSubsystem = Robot.getInstance().getClimbingSubsystem();
    requires(this.ClimbingSubsystem);
    this.HatchPanelSubsystem = Robot.getInstance().getHatchPanelSubsystem();
    requires(this.HatchPanelSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    this.HatchPanelSubsystem.jawsOff();
    this.ClimbingSubsystem.pneumaticsOff();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
