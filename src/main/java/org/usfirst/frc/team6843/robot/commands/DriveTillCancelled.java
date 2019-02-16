package org.usfirst.frc.team6843.robot.commands;

import org.usfirst.frc.team6843.robot.Robot;
import org.usfirst.frc.team6843.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Useful for driving straight while holding a button and stopping when
 * released.
 */
public class DriveTillCancelled extends Command {

    private final DriveSubsystem driveSubsystem;
    private final boolean forward;

    public DriveTillCancelled(boolean forward) {
        this.driveSubsystem = Robot.getInstance().getDriveSubsystem();
        requires(this.driveSubsystem);
        this.forward = forward;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        super.initialize();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (forward)
            this.driveSubsystem.velocityDrive(1000.0, -1000.0);
        else
            this.driveSubsystem.velocityDrive(-1000.0, 1000.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;

    }

    // Called once after isFinished returns true
    protected void end() {
        this.driveSubsystem.stop();
    }
}
