/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6843.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class HabLevelTwoAuto extends CommandGroup {
  /**
   * Add your docs here.
   */
  public HabLevelTwoAuto() {
    // addSequential(new InitiateAlphaProtocol());
    addSequential(new TimedRaiseRobotFront());
    addSequential(new TimedRaiseRobotRear());
    addSequential(new DriveCarriageFor(1, 1.0, 0.2));
    addSequential(new DriveCarriageFor(0.25, -0.6, -0.15));
    addSequential(new TimedLowerRobotFront());
    addSequential(new DriveCarriageFor(1.5, 1.0, 0.2));
    addSequential(new DriveCarriageFor(0.25, -0.6, -0.15));
    addSequential(new TimedLowerRobotRear());
    addSequential(new DriveTo(10));

    
  }
}