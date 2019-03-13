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
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.usfirst.frc.team6843.robot.RobotMap;
import org.usfirst.frc.team6843.robot.commands.MoveHatchMechanism;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Add your docs here.
 */
public class HatchPanelSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private Compressor compressor = new Compressor(RobotMap.COMPRESSOR);
  private Solenoid HatchJaws = new Solenoid(RobotMap.JAWS_SOLENOID);
  private DigitalInput LS1 = new DigitalInput(RobotMap.LIGHT_SENSOR_1);
  private DigitalInput LS2 = new DigitalInput(RobotMap.LIGHT_SENSOR_2);
  private DigitalInput LS3 = new DigitalInput(RobotMap.LIGHT_SENSOR_3);
  private DigitalInput LS4 = new DigitalInput(RobotMap.LIGHT_SENSOR_4);
  private DigitalInput LS5 = new DigitalInput(RobotMap.LIGHT_SENSOR_5);
  private DigitalInput LS6 = new DigitalInput(RobotMap.LIGHT_SENSOR_6);
  private DigitalInput LS7 = new DigitalInput(RobotMap.LIGHT_SENSOR_7); 
  private AnalogTrigger linearEncoderOutput = new AnalogTrigger(0);
  private Encoder linearEncoder = new Encoder(RobotMap.HATCH_LINEAR_ENCODER_PORT_1, RobotMap.HATCH_LINEAR_ENCODER_PORT_2);
  //private Counter linearEncoder = new Counter(EncodingType.k1X, new DigitalInput(RobotMap.HATCH_LINEAR_ENCODER_PORT_1), new DigitalInput(RobotMap.HATCH_LINEAR_ENCODER_PORT_2), false);
  private WPI_VictorSPX linearSlideMotor = new WPI_VictorSPX(RobotMap.HATCH_SLIDE_MOTOR); // FIXME WPI_TalonSRX(RobotMap.HATCH_SLIDE_MOTOR); 
  private DoubleSolenoid MechanismEngage = new DoubleSolenoid(4, 5); //RobotMap.HATCH_MECHANISM_TOGGLE_PORT_1, RobotMap.HATCH_MECHANISM_TOGGLE_PORT_2);
  ///private Encoder linearEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);

  public HatchPanelSubsystem(){
    linearEncoderOutput.setLimitsVoltage(2, 2.6);
    compressor.setClosedLoopControl(true);
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

  public double distanceToGoal() {
    double temp = 0, distance = 0, amttemp = 0, interval = 1;
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
    }
    distance = interval * (temp / amttemp);
    return distance;
  }
  
  public void goToGoal(){
    if(!atGoal() && (distanceToGoal() < 0)){
      linearSlideMotor.set(ControlMode.PercentOutput, .75);

    } else if(!atGoal() && (distanceToGoal() > 0)){
      linearSlideMotor.set(ControlMode.PercentOutput, -.75);
    } else {
      linearSlideMotor.set(ControlMode.PercentOutput, 0.0);
    }               
  }    

  public boolean atGoal(){
    if((linearDistance() > (distanceToGoal() - .25 )) && (linearDistance() < (distanceToGoal() + .25 ))){
      return true;
    } else {
      return false;
    }
  }

  public void updateDashboard(){
    SmartDashboard.putNumber("Distance from Home of Tape", distanceToGoal());
    SmartDashboard.putNumber("Distance", linearDistance());
    SmartDashboard.putBoolean("The Mechanism is Forward", mechanismForward());
  }

  public void jawsOff(){
    HatchJaws.set(false);
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
  public double linearDistance(){
    return (linearEncoder.get() * (1/82.8));
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

}
