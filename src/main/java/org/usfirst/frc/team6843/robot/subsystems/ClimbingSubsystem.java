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
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Add your docs here.
 */

 
public class ClimbingSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private boolean atFrontLegs = false;
  private boolean atRearLegs = false;
  private Compressor Compressor = new Compressor(RobotMap.COMPRESSOR);
  private DoubleSolenoid FrontLegs = new DoubleSolenoid(RobotMap.FRONT_LEGS_PORT_1, RobotMap.FRONT_LEGS_PORT_2);
  private DoubleSolenoid RearLegs = new DoubleSolenoid(RobotMap.REAR_LEGS_PORT_1, RobotMap.REAR_LEGS_PORT_2);
  private Solenoid MasterSolenoid = new Solenoid(RobotMap.MASTER_SOLENOID);
  private DigitalOutput SpikeLimit = new DigitalOutput(RobotMap.LIMIT_ENGAGER);
  private final WPI_TalonSRX LowerDriveMotor = new WPI_TalonSRX(RobotMap.LOWER_DRIVE_MOTOR_1);
  private Ultrasonic rearSonic = new Ultrasonic(RobotMap.REAR_ULTRASONIC_PORT_1, RobotMap.REAR_ULTRASONIC_PORT_2);
  private Ultrasonic frontSonic = new Ultrasonic(RobotMap.FRONT_ULTRASONIC_PORT_1, RobotMap.FRONT_ULTRASONIC_PORT_2);
  //private boolean testt = false;
  public ClimbingSubsystem(){
    LowerDriveMotor.setNeutralMode(NeutralMode.Brake);
    LowerDriveMotor.set(ControlMode.PercentOutput, 0.0);
  }

  @Override
  public void initDefaultCommand() {
    //setDefaultCommand(new MODriveLiftForward());
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
/*
  public void test(){
    testt = !testt;
    
  }

  public boolean showtest() {
  return testt; 
}
*/
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
    FrontLegs.set(DoubleSolenoid.Value.kReverse);
  }

  public void lowerFront(){ //For lowering front legs of robot, should make "FrontLegs" be in reverse position
    FrontLegs.set(DoubleSolenoid.Value.kForward);
  }

  public void raiseRear(){ //For raising rear legs of robot, should make "RearLegs" be in forward position
  RearLegs.set(Value.kForward);
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

  public void setMaster(boolean on) {
    MasterSolenoid.set(on);
  }

  public void limitDisengage(){
    SpikeLimit.set(false);
  }

  public void updateDashboard(){
    
    //SmartDashboard.putBoolean("6in. Limit Engaged?", SpikeLimit.get());
    SmartDashboard.putBoolean("Are Tanks Pressurized?", pressureSwitch());
    SmartDashboard.putBoolean("Is Rear down?", isRearDown());
    SmartDashboard.putBoolean("Is Front down?", isFrontDown());
    SmartDashboard.putBoolean("Master Solenoid", MasterSolenoid.get());
    SmartDashboard.putNumber("Front Ultrasonic", frontSonic.getRangeInches());
    SmartDashboard.putNumber("Rear Ultrasonic", rearSonic.getRangeInches());
  }
  

  public void drive(double speed){
    LowerDriveMotor.set(ControlMode.PercentOutput, speed);
  }

  public void driveTo(double position){
    LowerDriveMotor.set(ControlMode.Position, position);
  }
  
  public boolean isFrontDown(){
    if(FrontLegs.get() == DoubleSolenoid.Value.kForward){
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
    if(RearLegs.get() == Value.kForward){
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

  public void driveToFrontLegs(double speed){
    if(frontSonic.getRangeInches() < 6){
      LowerDriveMotor.set(ControlMode.PercentOutput, 0);
      atFrontLegs = true;
    } else {
      LowerDriveMotor.set(ControlMode.PercentOutput, speed);
    }
  }

  public boolean getAtFrontLegs(){
    return atFrontLegs;
  }

  public void changeAtFrontLegs(boolean value){
    atFrontLegs = value;
  }

  public void driveToRearLegs(double speed){
    if(rearSonic.getRangeInches() < 6){
      LowerDriveMotor.set(ControlMode.PercentOutput, 0);
      atRearLegs = true;
    } else {
      LowerDriveMotor.set(ControlMode.PercentOutput, speed);
    }
  }
  
  public boolean getAtRearLegs(){
    return atRearLegs;
  }

  public void changeAtRearLegs(boolean value){
    atRearLegs = value;
  }

  public boolean pressureSwitch(){ // returns false if tanks are lower than 120psi
    return ! Compressor.getPressureSwitchValue();
  }

}