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
  JoystickButton oRightTrigger = new JoystickButton(this.manipulator, Constants.OI.leftStickID);
  JoystickButton oRightStick = new JoystickButton(this.manipulator, Constants.OI.rightStickID);
  JoystickButton oLeftStick = new JoystickButton(this.manipulator, Constants.OI.rightTriggerID);

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

  public void stateMachine(){
    if(oButtonX.press() == true){
      if(oRightTrigger.press() == true){

      }
      else if(oButtonY.press() == true){

      }
      else{

      }
    }
    else if(oButtonY.press() == true){
      if(oButtonX.press() == true){

      }
      else if(oLeftBumper){

      }
      else{

      }
    }
    else if(oLeftBumper.press() == true){

    }
    else if(oRightTrigger.press() == true){

    }
    else if(oRightStick.press() == true){

    }
    else if(oLeftStick.press() == true){

    }
    else{
      System.out.println("You're not pressing the correct button.");
    }
    /*
    else if()
  switch(States){
    case 0:
      if (noButtons == true){
        States = 0;
        motor = 0;
        break;
      }else if(grabButton = true){
        States = 1;
        break;
      }else if(throwButton == true){
        States = 2;
        break;
      }else if(cancelBallButton == true){
        States = 0;
        motor = 0;
        break;
      }
    case 1:
      if (limitSwitch == false){
        States = 1;
        motor = -0.75;
        break;
      }else if(limitSwitch == true){
        States = 0;
        motor = 0;
        break;
      }else if(cancelBallButton == true){
        States = 0;
        motor = 0;
        break;
      }
    case 2:
      if (cancelBallButton == false){
        States = 2;
        motor = throttle;
        Timer.delay(2);
        done = true;
        break;
      }else if(done == true){
        motor = 0;
        done = false;
        States = 0;
        break;
      }else if(cancelBallButton == true){
        Timer.getFPGATimestamp();
        States = 0;
        motor = 0;
        break;
      }
    case 3:
      
    case 4:

    case 5:
    }
    /*
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
