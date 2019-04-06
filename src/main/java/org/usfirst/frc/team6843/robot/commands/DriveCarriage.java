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

public class DriveCarriage extends Command {
  private final DriveSubsystem driveSubsystem;
  private final ClimbingSubsystem climbingSubsystem;
  private double botVelocity;
  private double carriageVelocity;
  public DriveCarriage(double carriageSpeed, double driveSpeed) {
    this.driveSubsystem = Robot.getInstance().getDriveSubsystem();
    this.climbingSubsystem = Robot.getInstance().getClimbingSubsystem();
    requires(driveSubsystem);
    requires(climbingSubsystem);
    botVelocity = driveSpeed;
    carriageVelocity = carriageSpeed;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    this.climbingSubsystem.drive(-carriageVelocity);
    this.driveSubsystem.arcadeDrive(botVelocity, 0);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    this.climbingSubsystem.drive(0);
    this.driveSubsystem.arcadeDrive(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.climbingSubsystem.drive(0);
    this.driveSubsystem.arcadeDrive(0, 0);
  }
}
