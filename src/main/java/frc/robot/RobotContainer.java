// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//RobotContainer = OI, Constants = RobotMap

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Drive.StopDrive;
import frc.robot.commands.Hopper.StopHopper;
import frc.robot.commands.Intake.DeployIntake;
import frc.robot.commands.Intake.StopIntake;
import frc.robot.commands.Shooter.StopShooter;
import frc.robot.commands.Shooter.Shoot;
import frc.robot.commands.Hopper.ManualHopper;



/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  // The robot's subsystems and commands are defined here...
  Joystick driver = new Joystick(Constants.OI.driverUSB);
  Joystick manipulator = new Joystick(Constants.OI.manipulatorUSB);

  JoystickButton oButtonX = new JoystickButton(this.manipulator, Constants.OI.xButtonID);
  JoystickButton oButtonY = new JoystickButton(this.manipulator, Constants.OI.yButtonID);
  JoystickButton oLeftBumper = new JoystickButton(this.manipulator, Constants.OI.leftBumperID);

  public double GetDriverRawAxis(int axis){
      return this.driver.getRawAxis(axis);  
  }

  public double GetManipulatorRawAxis(int axis){
    return this.manipulator.getRawAxis(axis);
  }

  public boolean getManipulatorButton(int button) {
    return this.manipulator.getRawButton(button);
  }

  public boolean getDriverButton(int button) {
    return this.driver.getRawButton(button);
  }

  /** The container for the robot. Contains subsystems, OI devices, and commands. */

  public RobotContainer() {

    Robot.drivetrain.setDefaultCommand(new StopDrive());
    Robot.shooter.setDefaultCommand(new StopShooter());
    Robot.hopper.setDefaultCommand(new StopHopper());
    Robot.intake.setDefaultCommand(new StopIntake());


    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  public void configureButtonBindings() {
    oButtonX.whenHeld(new DeployIntake());
    oButtonY.whenHeld(new Shoot());
  }

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
