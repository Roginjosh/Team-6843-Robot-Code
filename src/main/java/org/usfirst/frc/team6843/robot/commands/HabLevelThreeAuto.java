/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6843.robot.commands;

import org.usfirst.frc.team6843.robot.commands.LimitDisengage;
import org.usfirst.frc.team6843.robot.commands.LimitEngage;
import org.usfirst.frc.team6843.robot.commands.RaiseRear;
import org.usfirst.frc.team6843.robot.commands.RaiseFront;
import org.usfirst.frc.team6843.robot.commands.LowerFront;
import org.usfirst.frc.team6843.robot.commands.LowerRear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class HabLevelThreeAuto extends CommandGroup {
  /**
   * Add your docs here.
   */
  public HabLevelThreeAuto() {
    addSequential(new InitiateProtocolAlpha());
    addSequential(new OpenJaws());
    addSequential(new ToggleFrontLegs());
    addParallel(new ToggleRearLegs());
    addSequential(new DriveCarriageFor(5, .25));
    addSequential(new LowerFront());
    addSequential(new DriveCarriageFor(5, .25));
    addSequential(new LowerRear());
    addSequential(new DriveTo(20));
  }
}
