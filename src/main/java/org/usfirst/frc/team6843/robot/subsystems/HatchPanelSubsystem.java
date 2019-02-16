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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.AnalogTriggerOutput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Add your docs here.
 */
public class HatchPanelSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private Compressor AlwaysOff = new Compressor(0);//RobotMap.HATCH_COMPRESSOR_1);
  private DoubleSolenoid HatchJaws = new DoubleSolenoid(5,6);
  private DigitalInput LS1 = new DigitalInput(0);
  private DigitalInput LS2 = new DigitalInput(1);
  private DigitalInput LS3 = new DigitalInput(2);
  private DigitalInput LS4 = new DigitalInput(3);
  private DigitalInput LS5 = new DigitalInput(4);
  private DigitalInput LS6 = new DigitalInput(5);
  private DigitalInput LS7 = new DigitalInput(6); 
  private AnalogTrigger linearEncoderOutput = new AnalogTrigger(0);
  private Counter linearEncoder = new Counter(linearEncoderOutput);
  private WPI_TalonSRX linearSlideMotor = new WPI_TalonSRX(RobotMap.HATCH_SLIDE_MOTOR); 
  private DoubleSolenoid MechanismEngage = new DoubleSolenoid(7,8);
  ///private Encoder linearEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
  
  

  public HatchPanelSubsystem(){
    //Turns off the compressor because we have no compressor on the compressor port of this PCM
    linearEncoderOutput.setLimitsVoltage(2, 2.6);
    /*
    linearEncoder.setMaxPeriod(.1);
    linearEncoder.setMinRate(10);
    linearEncoder.setDistancePerPulse(5);
    linearEncoder.setReverseDirection(true);
    linearEncoder.setSamplesToAverage(7);*/
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public double distanceToGoal() {
    double temp = 0, distance = 0, amttemp = 0, goal = 0, interval = 1;
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
    distance = interval * (temp / amttemp);
    return distance;
  }
  
  public void goToGoal(){
    if(!atGoal() && (distanceToGoal() < 0)){
      linearSlideMotor.set(ControlMode.PercentOutput, .75);

    } else if(!atGoal() && (distanceToGoal() > 0)){
      linearSlideMotor.set(ControlMode.PercentOutput, -.75);

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
    HatchJaws.set(Value.kOff);
  }

  public void openJaws() {
    HatchJaws.set(Value.kForward);
  }

  public void closeJaws() {
    HatchJaws.set(Value.kReverse);
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

  public double linearDistance(){
    return (linearEncoder.get() * (1/82.8));
  }

  public void clearLinearDistance(){
    linearEncoder.clearUpSource();
    linearEncoder.clearDownSource();
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

}
