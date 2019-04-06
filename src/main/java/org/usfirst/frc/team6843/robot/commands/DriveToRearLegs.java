package org.usfirst.frc.team6843.robot.commands;

import org.usfirst.frc.team6843.robot.Robot;
import org.usfirst.frc.team6843.robot.subsystems.ClimbingSubsystem;
import edu.wpi.first.wpilibj.command.Command;

public class DriveToRearLegs extends Command {

  private final ClimbingSubsystem climbingSubsystem;
  private final double velocity;

  public DriveToRearLegs(double speed) {
    this.climbingSubsystem = Robot.getInstance().getClimbingSubsystem();
    requires(this.climbingSubsystem);
    this.velocity = speed;
  }

  // Called just before this Cb ommand runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    this.climbingSubsystem.driveToRearLegs(velocity);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() { 
    return this.climbingSubsystem.getAtRearLegs();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    this.climbingSubsystem.changeAtRearLegs(false);
  }

}
