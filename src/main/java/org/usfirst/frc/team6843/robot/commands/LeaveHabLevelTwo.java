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
    addSequential(new InitiateAlphaProtocol());
    addSequential(new DriveTo(-8));
    addSequential(new LowerRear());
    addSequential(new DriveCarriageUntil(12, false));
    addSequential(new LowerFront());
    addSequential(new DriveCarriageUntil(30, false));
    addSequential(new RaiseFrontAndRear());
    
  }
}
