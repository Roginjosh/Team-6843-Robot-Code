/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6843.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeaveHabLevelTwo extends CommandGroup {
  /**
   * Add your docs here.
   */
  public LeaveHabLevelTwo() {
    // addSequential(new InitiateAlphaProtocol());
    addSequential(new DriveTo(-12));
    addSequential(new TimedRaiseRobotRear());
    addSequential(new DriveCarriageFor(1.5, -1.0, -0.2));
    addSequential(new TimedRaiseRobotFront());
    addSequential(new DriveCarriageFor(1.25, -1.0, -0.2));
    addSequential(new TimedLowerRobotFrontAndRear(2));
    addSequential(new DriveDrivebase(1.5, -0.5, 0.0));
    // addSequential(new TimedLowerRobotRear());
    // addSequential(new TimedLowerRobotFront());
    // addSequential(new DriveTo(-60));
    
  }
}