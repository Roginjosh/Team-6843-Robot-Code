/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6843.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class HabLevelThreeAuto extends CommandGroup {
  /**
   * Add your docs here.
   */
  public HabLevelThreeAuto() {
    addSequential(new InitiateAlphaProtocol());
    addSequential(new OpenJaws());
    addSequential(new LowerFrontAndRear());
    addSequential(new DriveCarriageUntil(18, true));
    addSequential(new RaiseFront());
    addSequential(new DriveCarriageUntil(2, true));
    addSequential(new RaiseRear());
    addSequential(new DriveTo(20));
  }
}
