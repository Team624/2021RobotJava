// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//RobotContainer = OI, Constants = RobotMap

package frc.robot;

import frc.robot.Robot;
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
  JoystickButton oLeftStick = new JoystickButton(this.manipulator, Constants.OI.L3BUttonID);
  JoystickButton oRightStick = new JoystickButton(this.manipulator, Constants.OI.R3BUttonID);



  public double GetDriverRawAxis(int axis){
      return this.driver.getRawAxis(axis);
  }

  public double GetManipulatorRawAxis(int axis){
    return this.manipulator.getRawAxis(axis);
  }

  public boolean getManipulatorButton(int axis) {
    return this.manipulator.getRawButton(axis);
  }

  public boolean getDriverButton(int axis) {
    return this.driver.getRawButton(axis);
  }


  /** The container for the robot. Contains subsystems, OI devices, and commands. */

  
  public RobotContainer() {
    // Configure the button bindings
    Robot.driveTrain.setDefaultCommand(new SwerveDrive());
    Robot.flywheel.setDefaultCommand(new StopFlywheel());
    Robot.hopper.setDefaultCommand(new StopHopper());
    Robot.flywheel.setDefaultCommand(new StopFlywheel());
    configureButtonBindings();

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */

  private void configureButtonBindings() {
    oButtonX.whenPressed(new ManualHopper());
=======
  public void configureButtonBindings() {
    // make Reverse hopper command
    // make Forward hopper command
    oButtonX.whenHeld(new DeployIntake());
    oButtonY.whenHeld(new Prime());
    oLeftBumper.whenHeld(new Shoot());
    if(oButtonY.get() && oLeftBumper.get()){
      oButtonY.whenHeld(new Prime());
      oLeftBumper.whenHeld(new Shoot());
    }
    if(oButtonX.get() && oLeftBumper.get()){
      oButtonX.whenHeld(new DeployIntake());
      oLeftBumper.whenHeld(new Shoot());
    }
    if(oButtonX.get() && oButtonY.get()){
      oButtonY.whenHeld(new Prime());
      oButtonX.whenHeld(new DeployIntake());
      
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
