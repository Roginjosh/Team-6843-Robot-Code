/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6843.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team6843.robot.RobotMap;
import org.usfirst.frc.team6843.robot.commands.MODriveLiftForward;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Add your docs here.
 */
public class ClimbingSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private Compressor Compressor = new Compressor(RobotMap.CLIMB_COMPRESSOR_1);
  private DoubleSolenoid FrontLegs = new DoubleSolenoid(1, 0);
  private DoubleSolenoid RearLegs = new DoubleSolenoid(2, 3);
  private Solenoid SpikeLimit = new Solenoid(4);
  private final WPI_TalonSRX LowerDriveMotor = new WPI_TalonSRX(RobotMap.LOWER_DRIVE_MOTOR_1);

  public ClimbingSubsystem(){
    LowerDriveMotor.setNeutralMode(NeutralMode.Brake);
    LowerDriveMotor.set(ControlMode.PercentOutput, 0.0);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new MODriveLiftForward());
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public boolean rearDownOrNah() {
    if(RearLegs.get() == Value.kForward){
      return true;
    } else {
      return false;
    }
  }

  public boolean frontDownOrNah() {
    if(FrontLegs.get() == Value.kForward){
      return true;
    } else {
      return false;
    }
  }
  public void pneumaticsOff(){
    FrontLegs.set(DoubleSolenoid.Value.kOff);
    RearLegs.set(DoubleSolenoid.Value.kOff);
  }

  public void raiseFront(){ //For raising front legs of robot, should make "FrontLegs" be in forward position
    FrontLegs.set(DoubleSolenoid.Value.kForward);
  }

  public void lowerFront(){ //For lowering front legs of robot, should make "FrontLegs" be in reverse position
    FrontLegs.set(DoubleSolenoid.Value.kReverse);
  }

  public void raiseRear(){ //For raising rear legs of robot, should make "RearLegs" be in forward position
  RearLegs.set(DoubleSolenoid.Value.kForward);
  }

  public void lowerRear(){ //For lowering Rear legs of robot, should make "RearLegs" be in reverse position
  RearLegs.set(DoubleSolenoid.Value.kReverse);
  }

  public void toggleLimit(){
    if (SpikeLimit.get() == false) {
      SpikeLimit.set(true);
    }
    else {
      SpikeLimit.set(false);
    }
  }

  public void limitEngage(){
    SpikeLimit.set(true);
  }

  public void limitDisengage(){
    SpikeLimit.set(false);
  }

  public void updateDashboard(){
    SmartDashboard.putBoolean("6in. Limit Engaged?", SpikeLimit.get());
  }

  public void drive(double speed){
    LowerDriveMotor.set(ControlMode.PercentOutput, speed);
  }

  public void driveTo(double position){
    LowerDriveMotor.set(ControlMode.Position, position);
  }
  
  public boolean isFrontDown(){
    if(FrontLegs.get() == Value.kForward){
      return true;
    } else {
      return false;
    }
  }
  
  public void ToggleFrontLegs(){
    if(isFrontDown()){
      FrontLegs.set(Value.kReverse);
    } else {
      FrontLegs.set(Value.kForward);
    }
  }

  public boolean isRearDown(){
    if(FrontLegs.get() == Value.kForward){
      return true;
    } else {
      return false;
    }
  }
  
  public void ToggleRearLegs(){
    if(isRearDown()){
      FrontLegs.set(Value.kReverse);
    } else {
      FrontLegs.set(Value.kForward);
    }
  }

}