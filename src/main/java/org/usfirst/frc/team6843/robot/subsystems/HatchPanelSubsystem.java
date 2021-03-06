/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6843.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX; 

import org.usfirst.frc.team6843.robot.RobotMap;
import org.usfirst.frc.team6843.robot.commands.MoveHatchMechanism;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Add your docs here.
 */
public class HatchPanelSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private double linearOffset = 0;
  private double manipulateOffsetVariable = 3.75;
  private Compressor compressor = new Compressor(RobotMap.COMPRESSOR);
  private Solenoid HatchJaws = new Solenoid(RobotMap.JAWS_SOLENOID);
  private DigitalInput LS1 = new DigitalInput(RobotMap.LIGHT_SENSOR_1);
  private DigitalInput LS2 = new DigitalInput(RobotMap.LIGHT_SENSOR_2);
  private DigitalInput LS3 = new DigitalInput(RobotMap.LIGHT_SENSOR_3);
  private DigitalInput LS4 = new DigitalInput(RobotMap.LIGHT_SENSOR_4);
  private DigitalInput LS5 = new DigitalInput(RobotMap.LIGHT_SENSOR_5);
  private DigitalInput LS6 = new DigitalInput(RobotMap.LIGHT_SENSOR_6);
  private DigitalInput LS7 = new DigitalInput(RobotMap.LIGHT_SENSOR_7);
  private DigitalInput leftLimit = new DigitalInput(RobotMap.LEFT_HATCH_LIMIT);
  private DigitalInput rightLimit = new DigitalInput(RobotMap.RIGHT_HATCH_LIMIT); 
  //private AnalogTrigger linearEncoderOutput = new AnalogTrigger(0);
  private Encoder linearEncoder = new Encoder(RobotMap.HATCH_LINEAR_ENCODER_PORT_1, RobotMap.HATCH_LINEAR_ENCODER_PORT_2);
  //private Counter linearEncoder = new Counter(EncodingType.k1X, new DigitalInput(RobotMap.HATCH_LINEAR_ENCODER_PORT_1), new DigitalInput(RobotMap.HATCH_LINEAR_ENCODER_PORT_2), false);
  private WPI_TalonSRX linearSlideMotor = new WPI_TalonSRX(RobotMap.HATCH_SLIDE_MOTOR); 
  private DoubleSolenoid MechanismEngage = new DoubleSolenoid(4, 5); //RobotMap.HATCH_MECHANISM_TOGGLE_PORT_1, RobotMap.HATCH_MECHANISM_TOGGLE_PORT_2);
  ///private Encoder linearEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
  

  public HatchPanelSubsystem(){
   // linearEncoderOutput.setLimitsVoltage(2, 2.6);
    compressor.setClosedLoopControl(true);
    linearEncoder.reset();
    /*
    linearEncoder.setMaxPeriod(.1);
    linearEncoder.setMinRate(10);
    linearEncoder.setDistancePerPulse(5);
    linearEncoder.setReverseDirection(true);
    linearEncoder.setSamplesToAverage(7);*/
  }
  
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new MoveHatchMechanism());
  }

  public double getManipulatedOffsetValue() {
    return manipulateOffsetVariable;
  }


  public double locationOfLine() {
    double temp = 0, location = 0, amttemp = 0, interval = 1.25;
    if(!LS1.get()){
      temp += -3;
      amttemp++;
    }
    if(!LS2.get()){
      temp += -2;
      amttemp++;
    }
    if(!LS3.get()){
      temp += -1;
      amttemp++;
    }
    if(!LS4.get()){
      temp += 0;
      amttemp++;
    }
    if(!LS5.get()){
      temp += 1;
      amttemp++;
    }
    if(!LS6.get()){
      temp += 2;
      amttemp++;
    }
    if(!LS7.get()){
      temp += 3;
      amttemp++;
    }
    if (amttemp == 0) {
      return 0;
    } else {
    location = interval * (temp / amttemp);
    return location;
    }
  }
  
  public void goToGoal(){
    if(!isThereALine()){  //There is no line to track to
      if(Math.abs(linearPosition()) < 0.1){
        linearSlideMotor.set(ControlMode.PercentOutput, 0);
      } else if(linearPosition() < 0) {
        linearSlideMotor.set(ControlMode.PercentOutput, -.75);
      } else if (linearPosition() > 0) {
        linearSlideMotor.set(ControlMode.PercentOutput, .75); }
    } else {     //There is a line
      if((linearPosition() > locationOfLine()) && !onLine() && !getLeftHatchLimit()){
        linearSlideMotor.set(ControlMode.PercentOutput, .35);
      } else if ((linearPosition() < locationOfLine()) && !onLine() && !getRightHatchLimit()){
        linearSlideMotor.set(ControlMode.PercentOutput, -.35);
      } else {
        linearSlideMotor.set(ControlMode.PercentOutput, 0);
      }
    }
   
    /*if(!atGoal() && (locationOfLine() < 0)){
      linearSlideMotor.set(ControlMode.PercentOutput, .35);

    } else if(!atGoal() && (locationOfLine() > 0)){
      linearSlideMotor.set(ControlMode.PercentOutput, -.35);
    } else {
      linearSlideMotor.set(ControlMode.PercentOutput, 0.0);
    }      */         
  }    

  public double distanceToGoal(){
    return -(locationOfLine() - linearPosition());
  }

  public boolean isThereALine() {
    if(LS1.get() && LS2.get() && LS3.get() && LS4.get() && LS5.get() && LS6.get() && LS7.get()){
    return false;
    } else {
      return true;
    }
  }

  public boolean onLine(){
    if (Math.abs(locationOfLine() - linearPosition()) < .75){
      return true;
    } else {
      return false;
    }
    
  }

  public void manipulateOffsetVariable(double value){
    linearOffset = value;
  }

  public void updateDashboard(){
    SmartDashboard.putNumber("Location of Line", locationOfLine());
    SmartDashboard.putBoolean("The Mechanism is Forward", mechanismForward());
    SmartDashboard.putNumber("Linerar Position", linearPosition());
    SmartDashboard.putBoolean("Are we at the line?", onLine());
    SmartDashboard.putNumber("Distance to Goal", distanceToGoal());
    SmartDashboard.putBoolean("Left Hatch Limit", !leftLimit.get());
    SmartDashboard.putBoolean("Right Hatch Limit", !rightLimit.get());
    /*SmartDashboard.putBoolean("Light Sensor 1", LS1.get());
    SmartDashboard.putBoolean("Light Sensor 2", LS2.get());
    SmartDashboard.putBoolean("Light Sensor 3", LS3.get());
    SmartDashboard.putBoolean("Light Sensor 4", LS4.get());
    SmartDashboard.putBoolean("Light Sensor 5", LS5.get());
    SmartDashboard.putBoolean("Light Sensor 6", LS6.get());
    SmartDashboard.putBoolean("Light Sensor 7", LS7.get());*/
    SmartDashboard.putNumber("Raw Linear Encoder", linearEncoderValue());


  }


  public void smartResetHatchEncoder(){
    if (!getRightHatchLimit()) {
      linearSlideMotor.set(ControlMode.PercentOutput, -0.35);
    }
  }

  public double linearEncoderValue(){
    return linearEncoder.get();
  }

  public void jawsOff(){
    HatchJaws.set(false);
  }

  public boolean getRightHatchLimit(){
    return !rightLimit.get();
  }
  
  public boolean getLeftHatchLimit(){
    return !leftLimit.get();
  }

  public void openJaws() {
    HatchJaws.set(true);
  }

  public void closeJaws() {
    HatchJaws.set(false);
  }

  /** 
    Here put either L to go left or R to go right
  */
  public void slideHatchMechanism(char direction){
    if((direction == 'L') || (direction == 'l')){
     // linearSlideMotor.set(ControlMode.PercentOutput, .25);
    } else if ((direction == 'R') || (direction == 'r')) {
     // linearSlideMotor.set(ControlMode.PercentOutput, .25);
    }
  }
//John has a smol pp
  public double linearPosition(){
    return ((linearEncoder.get() * (-1.0/82.8)) + linearOffset);   //OHNO Majik Number
  }

  public void clearLinearDistance(){
    //linearEncoder.clearUpSource();
    //linearEncoder.clearDownSource();
    linearEncoder.reset();
  }

  public boolean mechanismForward(){
    if(MechanismEngage.get() == Value.kForward){
      return true;
    } else {
      return false;
    }
  }

  
  public void ToggleMechanism(){
    if(mechanismForward()){
      MechanismEngage.set(Value.kReverse);
    } else {
      MechanismEngage.set(Value.kForward);
    }
  }

  public void PullMechanism(){
    MechanismEngage.set(Value.kReverse);
  }

  public void PushMechanism(){
    MechanismEngage.set(Value.kForward);
  }

}
