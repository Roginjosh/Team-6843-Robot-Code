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
    addSequential(new InitiateAlphaProtocol());
    addSequential(new ModulatedRobotUp(10));
    addSequential(new DriveCarriageFor(10, 0.4));//DriveCarriageUntil(18, true));
    addSequential(new LowerRobotFront());
    addSequential(new WaitCommand(5));
    addSequential(new DriveCarriageFor(10, 0.4)); //Until(2, true));
    addSequential(new LowerRobotRear());
    addSequential(new WaitCommand(5));
    addSequential(new DriveTo(10));

    
  }
}