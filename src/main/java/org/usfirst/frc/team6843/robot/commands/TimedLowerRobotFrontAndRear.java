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

public class TimedLowerRobotFrontAndRear extends Command {
  protected ClimbingSubsystem climbingSubsystem;
  protected double time;

  public TimedLowerRobotFrontAndRear(double time) {
    this.climbingSubsystem = Robot.getInstance().getClimbingSubsystem();
    requires(climbingSubsystem);
    this.time = time;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    setTimeout(time);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    this.climbingSubsystem.setMaster(true);
    this.climbingSubsystem.lowerFront();
    this.climbingSubsystem.lowerRear();
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

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
