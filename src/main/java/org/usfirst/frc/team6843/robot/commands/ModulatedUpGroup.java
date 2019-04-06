/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6843.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class ModulatedUpGroup extends CommandGroup {
  /**
   * Add your docs here.
   */
  public ModulatedUpGroup() {
   addParallel(new RaiseRobotFront());
   addSequential(new WaitCommand(0.5));
   addSequential(new RaiseRobotRear());
   //addSequential(new ModulatedRobotUp(10.0));
  }
}
