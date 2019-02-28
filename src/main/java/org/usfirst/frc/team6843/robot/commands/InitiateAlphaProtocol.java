/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6843.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class InitiateAlphaProtocol extends CommandGroup {
  /**
   * Add your docs here.
   */
  public InitiateAlphaProtocol() {
    addParallel(new LimitDisengage());
    addParallel(new LowerRobotFront());
    addParallel(new LowerRobotRear());
    addParallel(new PullHatchMechanism());
    addSequential(new CloseJaws());


  }
}
