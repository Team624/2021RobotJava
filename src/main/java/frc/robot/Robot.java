// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Feeder;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.AnalogInput;
import frc.robot.commands.Feeder.Shoot;
import frc.robot.commands.Feeder.StopFeeder;
import frc.robot.commands.Hopper.ForwardHopper;
import frc.robot.commands.Hopper.IdleHopper;
import frc.robot.commands.Shooter.Prime;
import frc.robot.commands.Shooter.StopShooter;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public static DriveSubsystem drivetrain = new DriveSubsystem();
  public static Hopper hopper = new Hopper();
  public static Shooter shooter = new Shooter();
  public static Intake intake = new Intake();
  public static Feeder feeder = new Feeder();
  

  Compressor compressor = new Compressor(0);

  public static AnalogInput analog = new AnalogInput(1);

  //private Command m_autonomousCommand;
  public static RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    compressor.setClosedLoopControl(true);
  
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {}

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    if(Timer.getFPGATimestamp() < 2.5){
      new Prime();
    }else if(Timer.getFPGATimestamp() < 7.5 && Timer.getFPGATimestamp() > 2.5){
      new Shoot();
      new ForwardHopper();
    }else if(Timer.getFPGATimestamp() < 9 && Timer.getFPGATimestamp() > 7.5){
      new StopShooter();
      new StopFeeder();
      new IdleHopper();
      drivetrain.drive(.1, .1, 0, true);
    }
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
