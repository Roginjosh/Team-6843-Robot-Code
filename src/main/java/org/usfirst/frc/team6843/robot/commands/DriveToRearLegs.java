package org.usfirst.frc.team6843.robot.commands;

import org.usfirst.frc.team6843.robot.Robot;
import org.usfirst.frc.team6843.robot.subsystems.ClimbingSubsystem;
import org.usfirst.frc.team6843.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class DriveToRearLegs extends Command {

  private final ClimbingSubsystem climbingSubsystem;
  private final DriveSubsystem driveSubsystem;
  private final double carriageVelocity;
  private final double botVelocity;

  public DriveToRearLegs(double carriageSpeed, double driveSpeed) {
    this.climbingSubsystem = Robot.getInstance().getClimbingSubsystem();
    requires(this.climbingSubsystem);
    this.driveSubsystem = Robot.getInstance().getDriveSubsystem();
    requires(this.driveSubsystem);
    this.carriageVelocity = carriageSpeed;
    this.botVelocity = driveSpeed;
  }

  // Called just before this Cb ommand runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    this.driveSubsystem.arcadeDrive(botVelocity, 0);
    this.climbingSubsystem.driveToRearLegs(carriageVelocity);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() { 
    return this.climbingSubsystem.getAtRearLegs();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    this.driveSubsystem.arcadeDrive(0, 0);
    this.climbingSubsystem.changeAtRearLegs(false);
  }

}
