/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6843.robot.commands;

import org.usfirst.frc.team6843.robot.Robot;
import org.usfirst.frc.team6843.robot.subsystems.ClimbingSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class DriveCarriageUntil extends Command {
  protected ClimbingSubsystem climbingSubsystem;
  double inches;
  boolean direction;

  public DriveCarriageUntil(double inchesFromWall, boolean forward) {
    this.climbingSubsystem = Robot.getInstance().getClimbingSubsystem();
    requires(climbingSubsystem);
    this.inches = inchesFromWall;
    this.direction = forward;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    this.climbingSubsystem.driveUntil(inches, direction);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(this.climbingSubsystem.getInchesFromWall() <= inches) {
      return true;
    } else {
      return false;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    this.climbingSubsystem.drive(0.0);
  }
}
