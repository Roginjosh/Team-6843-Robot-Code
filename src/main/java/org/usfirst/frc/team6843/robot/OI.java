/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6843.robot;

import org.usfirst.frc.team6843.robot.commands.ApproachTarget;
import org.usfirst.frc.team6843.robot.commands.CloseJaws;
import org.usfirst.frc.team6843.robot.commands.DriveCarriage;
import org.usfirst.frc.team6843.robot.commands.HabLevelTwoAuto;
import org.usfirst.frc.team6843.robot.commands.InitiateAlphaProtocol;
import org.usfirst.frc.team6843.robot.commands.KillAll;
import org.usfirst.frc.team6843.robot.commands.LeaveHabLevelTwo;
import org.usfirst.frc.team6843.robot.commands.LimitDisengage;
import org.usfirst.frc.team6843.robot.commands.LimitEngage;
import org.usfirst.frc.team6843.robot.commands.LowerRobotFront;
import org.usfirst.frc.team6843.robot.commands.LowerRobotFrontAndRear;
import org.usfirst.frc.team6843.robot.commands.LowerRobotRear;
import org.usfirst.frc.team6843.robot.commands.ModulatedUpGroup;
import org.usfirst.frc.team6843.robot.commands.OpenJaws;
import org.usfirst.frc.team6843.robot.commands.PullHatchMechanism;
import org.usfirst.frc.team6843.robot.commands.PushHatchMechanism;
import org.usfirst.frc.team6843.robot.commands.RaiseRobotFront;
import org.usfirst.frc.team6843.robot.commands.ResetGyro;
import org.usfirst.frc.team6843.robot.commands.ResetRotatedToTarget;
import org.usfirst.frc.team6843.robot.commands.RotateTo;
import org.usfirst.frc.team6843.robot.commands.SmartResetHatchEncoder;
import org.usfirst.frc.team6843.robot.triggers.TwoButtonTrigger;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	/** Used to enforce drive stick dead zones. */
	public static final double DEAD_ZONE = 0.1;
	public static final int DRIVE_AXIS = 1;
	public static final int CURVE_AXIS = 4;

	/** Use these to turn the front throttles into buttons. */
	private static final int LEFT_FRONT_THROTTLE = 2;
	private static final int RIGHT_FRONT_THROTTLE = 3;
	private static final double THROTTLE_AS_BUTTON_THRESHOLD = 0.5;

	private final XboxController driver = new XboxController(0); 
	// The above line ^ creates a new controller on USB port 0, or the first controller plugged in
	private final Button driverY = new JoystickButton(driver, 4);
	private final Button driverB = new JoystickButton(driver, 2);
	private final Button driverA = new JoystickButton(driver, 1);
	private final Button driverX = new JoystickButton(driver, 3);
	private final Button driverBumperLeft = new JoystickButton(driver, 5);
	private final Button driverBumperRight = new JoystickButton(driver, 6);
	private final Button driverBack = new JoystickButton(driver, 7);
	private final Button driverStart = new JoystickButton(driver, 8);
	private final Trigger driverLeftThrottleButton = new ThrottleButton(driver, LEFT_FRONT_THROTTLE);
	private final Trigger driverRightThrottleButton = new ThrottleButton(driver, RIGHT_FRONT_THROTTLE);
	private final Button driverPOV0 = new POVButton(driver, 0);
	private final Button driverPOV90 = new POVButton(driver, 90);
	private final Button driverPOV180 = new POVButton(driver, 180);
	private final Button driverPOV270 = new POVButton(driver, 270);


	private final XboxController secondary = new XboxController(1);
	private final Button secondaryY = new JoystickButton(secondary, 4);
	private final Button secondaryB = new JoystickButton(secondary, 2);
	private final Button secondaryA = new JoystickButton(secondary, 1);
	private final Button secondaryX = new JoystickButton(secondary, 3);
	private final Button secondaryBumperLeft = new JoystickButton(secondary, 5);
	private final Button secondaryBumperRight = new JoystickButton(secondary, 6);
	private final Button secondaryBack = new JoystickButton(secondary, 7);
	private final Button secondaryStart = new JoystickButton(secondary, 8);
	//private final Trigger secondaryLeftThrottleButton = new ThrottleButton(secondary, LEFT_FRONT_THROTTLE);
	//private final Trigger secondaryRightThrottleButton = new ThrottleButton(secondary, RIGHT_FRONT_THROTTLE);
	private final Button secondaryPOV0 = new POVButton(secondary, 0);
	private final Button secondaryPOV90 = new POVButton(secondary, 90);
	private final Button secondaryPOV180 = new POVButton(secondary, 180);
	private final Button secondaryPOV270 = new POVButton(secondary, 270);
	/** Two button trigger with POV90 buttons on both controllers */
	private final Trigger twoButtonTrigger = new TwoButtonTrigger(secondaryPOV90, driverPOV0); 
	private final Button secondaryLJoyClick = new JoystickButton(secondary, 9);
	private final Button secondaryRJoyClick = new JoystickButton(secondary, 10);
	

	public OI() { // this is where you assign commands to buttons
		driverY.whenPressed(new RotateTo(0.0));
		driverB.whenPressed(new RotateTo(90.0));
		driverA.whenPressed(new RotateTo(180.0));
		driverX.whenPressed(new RotateTo(-90.0));
		driverPOV90.whenPressed(new LowerRobotFront());
		driverPOV180.whileHeld(new DriveCarriage(0.6, 0.15));
		driverPOV270.whenPressed(new LowerRobotRear());
		driverBumperLeft.whenPressed(new RotateTo(-28.75)); // Near left rocket
		driverLeftThrottleButton.whenActive(new RotateTo(-151.25)); // Far left rocket
		driverBumperRight.whenPressed(new RotateTo(28.75)); // Near right rocket
		driverRightThrottleButton.whenActive(new RotateTo(151.25)); // Far right rocket
		
		//driverBack.whenPressed(new DriveTo(-100));
		//driverStart.whenPressed(new DriveTo(100));
		//driverBack.whileHeld(new DriveTillCancelled(false));
		driverBack.whenPressed(new KillAll());
		//driverStart.whileHeld(new DriveTillCancelled(true));
		driverStart.whileHeld(new ApproachTarget());
		driverStart.whenReleased(new ResetRotatedToTarget());
		//driverPOV90.whenPressed(new ResetGyro()); // Has been changed to a two button trigger
		
		//below this line is for secondary controller
		secondaryA.whenPressed(new OpenJaws());//(new RaiseRobotRear());//whenPressed(new DriveCarriageFor(5, .25));
		secondaryB.whenPressed(new CloseJaws());
		secondaryX.whenPressed(new PushHatchMechanism());
		secondaryY.whenPressed(new PullHatchMechanism());
		// secondaryA.whenPressed(new RaiseRobotFront());
		// secondaryB.whenPressed(new LowerRobotFront());
		// secondaryX.whenPressed(new RaiseRobotRear());
		// secondaryY.whenPressed(new LowerRobotRear());
		secondaryPOV0.whenPressed(new InitiateAlphaProtocol());
		secondaryPOV180.whenPressed(new HabLevelTwoAuto());// HabLevelThreeAuto());
		secondaryPOV270.whenPressed(new SmartResetHatchEncoder());
		secondaryBumperLeft.whenPressed(new LowerRobotFrontAndRear());
		secondaryBumperRight.whileHeld(new ModulatedUpGroup());
		// The below two are temporary
		secondaryBack.whenPressed(new LimitDisengage());//HabLevelThreeAuto());
		secondaryStart.whenPressed( new LimitEngage());
secondaryLJoyClick.whenPressed(new LeaveHabLevelTwo());
		secondaryRJoyClick.whenPressed(new RaiseRobotFront());


		twoButtonTrigger.whenActive(new ResetGyro());
		
	}

	/**
	 * @return the drive power using cubed inputs.
	 */
	public double getDrivePower() {
		double drivePower = -driver.getRawAxis(DRIVE_AXIS);
		if (Math.abs(drivePower) < DEAD_ZONE) {
			drivePower = 0.0;
		}
		return Math.pow(drivePower, 3.0);
	}

	/**
	 * @return the drive power using inputs to the fifth.
	 */
	public double getCurvePower() {
		double curvePower = driver.getRawAxis(CURVE_AXIS);
		if (Math.abs(curvePower) < DEAD_ZONE) {
			curvePower = 0.0;
		}
		return Math.pow(curvePower, 5.0);
	}

	public double getSecondaryTriggerAxisValue() {
		return secondary.getRawAxis(2);
	}
	
	/**
	 * A custom trigger to turn a throttle into a button.
	 */
	private static class ThrottleButton extends Trigger {
		private final XboxController stick;
		private final int axis;

		public ThrottleButton(final XboxController stick, int axis) {
			this.stick = stick;
			this.axis = axis;
		}

		public boolean get() {
			return Math.abs(this.stick.getRawAxis(this.axis)) > THROTTLE_AS_BUTTON_THRESHOLD;
		}
	}
}
