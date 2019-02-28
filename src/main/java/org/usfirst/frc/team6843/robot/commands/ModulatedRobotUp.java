/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6843.robot.commands;

import org.usfirst.frc.team6843.robot.Robot;
import org.usfirst.frc.team6843.robot.subsystems.ClimbingSubsystem;
import org.usfirst.frc.team6843.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ModulatedRobotUp extends Command {
  protected ClimbingSubsystem climbingSubsystem;
  protected DriveSubsystem driveSubsystem;

  public ModulatedRobotUp() {
    this.climbingSubsystem = Robot.getInstance().getClimbingSubsystem();
    this.driveSubsystem = Robot.getInstance().getDriveSubsystem();
    requires(this.climbingSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.climbingSubsystem.raiseFront();
    this.climbingSubsystem.raiseRear();
        setTimeout(20.0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double pitch = this.driveSubsystem.getPitch();
    if (pitch < .05) {
      this.climbingSubsystem.setMaster(false);
    } else if (pitch > .75) {
      this.climbingSubsystem.setMaster(true);   
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

}