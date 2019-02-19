/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6843.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//CAN Bus
	public static final int PNEUMATICS_CONTROL_MOTOR = 0;
	public static final int LEFT_MOTOR_1 = 10;
	public static final int RIGHT_MOTOR_1 = 11;
	public static final int HATCH_SLIDE_MOTOR = 12;
	public static final int LOWER_DRIVE_MOTOR_1 = 13;
	public static final int COMPRESSOR = 0;
	//DIO Ports
	public static final int LIGHT_SENSOR_1 = 0;
	public static final int LIGHT_SENSOR_2 = 1;
	public static final int LIGHT_SENSOR_3 = 2;
	public static final int LIGHT_SENSOR_4 = 3;
	public static final int LIGHT_SENSOR_5 = 4;
	public static final int LIGHT_SENSOR_6 = 5;
	public static final int LIGHT_SENSOR_7 = 6;
	public static final int HATCH_LINEAR_ENCODER_PORT_1 = 7;
	public static final int HATCH_LINEAR_ENCODER_PORT_2 = 8;
	public static final int CARRIAGE_ULTRASONIC_PORT_1 = 9;
	public static final int CARRIAGE_ULTRASONIC_PORT_2 = 10;
	public static final int LIMIT_ENGAGER = 11;
	//PCM Ports
	public static final int FRONT_LEGS_PORT_1 = 0;
	public static final int FRONT_LEGS_PORT_2 = 1;
	public static final int REAR_LEGS_PORT_1 = 2;
	public static final int REAR_LEGS_PORT_2 = 3;
	public static final int HATCH_MECHANISM_TOGGLE_PORT_1 = 4;
	public static final int HATCH_MECHANISM_TOGGLE_PORT_2 = 5;
	public static final int JAWS_SOLENOID = 6;
	

	}
