// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//RobotContainer = OI, Constants = RobotMap

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Hopper.ForwardHopper;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.commands.Shooter.Prime;
import frc.robot.commands.Feeder.Shoot;
import frc.robot.commands.Intake.IdleIntake;
import frc.robot.commands.Intake.ManualIntake;
import frc.robot.commands.Intake.DeployIntake;
import frc.robot.commands.Shooter.StopShooter;
import frc.robot.commands.Drivetrain.TrackingSwerve;
import frc.robot.commands.Feeder.IdleFeeder;
import frc.robot.commands.Hopper.IdleHopper;
import frc.robot.commands.Hopper.ClearHopper;



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

  JoystickButton dButtonLeftBumper = new JoystickButton(this.driver, Constants.OI.leftBumperID);
  JoystickButton dButtonA = new JoystickButton(this.driver, Constants.OI.aButtonID);
  JoystickButton dButtonX = new JoystickButton(this.driver, Constants.OI.xButtonID);
  JoystickButton dButtonY = new JoystickButton(this.driver, Constants.OI.yButtonID);

  JoystickButton oButtonLeftBumper = new JoystickButton(this.manipulator, Constants.OI.leftBumperID);
  JoystickButton oButtonRightBumper = new JoystickButton(this.manipulator, Constants.OI.rightBumperID);
  JoystickButton oButtonA = new JoystickButton(this.manipulator, Constants.OI.aButtonID);
  JoystickButton oButtonX = new JoystickButton(this.manipulator, Constants.OI.xButtonID);
  JoystickButton oButtonY = new JoystickButton(this.manipulator, Constants.OI.yButtonID);
  JoystickButton oLeftStickButton = new JoystickButton(this.manipulator, Constants.OI.leftStickButtonID);
  JoystickButton oRightStickButton = new JoystickButton(this.manipulator, Constants.OI.rightStickButtonID);


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
    Robot.shooter.setDefaultCommand(new StopShooter());
    Robot.feeder.setDefaultCommand(new IdleFeeder());
    Robot.hopper.setDefaultCommand(new IdleHopper());
    Robot.intake.setDefaultCommand(new IdleIntake());
    Robot.drivetrain.setDefaultCommand(new RunCommand(() ->
                Robot.drivetrain.drive(
                    GetDriverRawAxis(1),
                    -GetDriverRawAxis(0),
                    GetDriverRawAxis(4),
                    true),
                    Robot.drivetrain)
    );
    configureButtonBindings();
  }

  /**
   * Use this method to define your butto
   * n->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  public void configureButtonBindings() {
    oButtonX.whenHeld(new DeployIntake());
    oButtonLeftBumper.whenHeld(new Prime());
    oButtonLeftBumper.whenHeld(new TrackingSwerve());
    oButtonA.whenHeld(new ForwardHopper());
    oButtonA.whenHeld(new Shoot());
    oButtonRightBumper.whenHeld(new ClearHopper());
    oLeftStickButton.whenHeld(new ManualIntake());

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
