// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//RobotContainer = OI, Constants = RobotMap

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Feeder.StopFeeder;
import frc.robot.commands.Hopper.ClearHopper;
import frc.robot.commands.Hopper.ForwardHopper;
import frc.robot.commands.Hopper.StopHopper;
import frc.robot.commands.Shooter.IdleShooter;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.commands.Shooter.Prime;
import frc.robot.commands.Feeder.Shoot;
import frc.robot.commands.Intake.IdleIntake;
import frc.robot.commands.Intake.DeployIntake;



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
    Robot.shooter.setDefaultCommand(new IdleShooter());
    Robot.feeder.setDefaultCommand(new StopFeeder());
    Robot.hopper.setDefaultCommand(new StopHopper());
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
    oLeftBumper.whenHeld(new ClearHopper());
    dButtonLeftBumper.whenHeld(new Prime());
    if(GetDriverRawAxis(Constants.OI.RightTriggerID) > .5){
      new ForwardHopper();
      new Shoot();
    }
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
