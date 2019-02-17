/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6843.robot.commands;

import org.usfirst.frc.team6843.robot.OI;
import org.usfirst.frc.team6843.robot.Robot;

import org.usfirst.frc.team6843.robot.subsystems.ClimbingSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class MODriveLiftForward extends Command {
  protected ClimbingSubsystem ClimbingSubsystem;
  protected OI oi;
  public MODriveLiftForward() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.ClimbingSubsystem = Robot.getInstance().getClimbingSubsystem();
    requires(this.ClimbingSubsystem);
    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    super.initialize();
    this.oi = Robot.getInstance().getOI();

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(this.ClimbingSubsystem.rearDownOrNah() == true) {
      ClimbingSubsystem.drive(Math.abs(.25*this.oi.getSecondaryTriggerAxisValue()));
    }
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
