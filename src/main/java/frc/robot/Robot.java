// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj.Compressor;
/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public static Drivetrain drivetrain = new Drivetrain();
  public static Hopper hopper = new Hopper();
  public static Flywheel flywheel = new Flywheel();
  public static Intake intake = new Intake();

  Compressor compressor = new Compressor(0);

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

    Robot.flywheel.leftFlywheel.configFactoryDefault();
    Robot.flywheel.rightFlywheel.configFactoryDefault();

    Robot.flywheel.leftFlywheel.configNeutralDeadband(0.001);
    Robot.flywheel.rightFlywheel.configNeutralDeadband(0.001);

    Robot.flywheel.leftFlywheel.configNominalOutputForward(0, Constants.FlywheelConstants.kTimeoutMs);
		Robot.flywheel.leftFlywheel.configNominalOutputReverse(0, Constants.FlywheelConstants.kTimeoutMs);
		Robot.flywheel.leftFlywheel.configPeakOutputForward(1, Constants.FlywheelConstants.kTimeoutMs);
    Robot.flywheel.leftFlywheel.configPeakOutputReverse(-1, Constants.FlywheelConstants.kTimeoutMs);
    Robot.flywheel.rightFlywheel.configNominalOutputForward(0, Constants.FlywheelConstants.kTimeoutMs);
		Robot.flywheel.rightFlywheel.configNominalOutputReverse(0, Constants.FlywheelConstants.kTimeoutMs);
		Robot.flywheel.rightFlywheel.configPeakOutputForward(1, Constants.FlywheelConstants.kTimeoutMs);
    Robot.flywheel.rightFlywheel.configPeakOutputReverse(-1, Constants.FlywheelConstants.kTimeoutMs);
    
    Robot.flywheel.leftFlywheel.config_kF(Constants.FlywheelConstants.kPIDLoopIdx, Constants.FlywheelConstants.kGains_Velocit.kF, Constants.FlywheelConstants.kTimeoutMs);
		Robot.flywheel.leftFlywheel.config_kP(Constants.FlywheelConstants.kPIDLoopIdx, Constants.FlywheelConstants.kGains_Velocit.kP, Constants.FlywheelConstants.kTimeoutMs);
		Robot.flywheel.leftFlywheel.config_kI(Constants.FlywheelConstants.kPIDLoopIdx, Constants.FlywheelConstants.kGains_Velocit.kI, Constants.FlywheelConstants.kTimeoutMs);
		Robot.flywheel.leftFlywheel.config_kD(Constants.FlywheelConstants.kPIDLoopIdx, Constants.FlywheelConstants.kGains_Velocit.kD, Constants.FlywheelConstants.kTimeoutMs);
   
    Robot.flywheel.rightFlywheel.config_kF(Constants.FlywheelConstants.kPIDLoopIdx, Constants.FlywheelConstants.kGains_Velocit.kF, Constants.FlywheelConstants.kTimeoutMs);
		Robot.flywheel.rightFlywheel.config_kP(Constants.FlywheelConstants.kPIDLoopIdx, Constants.FlywheelConstants.kGains_Velocit.kP, Constants.FlywheelConstants.kTimeoutMs);
		Robot.flywheel.rightFlywheel.config_kI(Constants.FlywheelConstants.kPIDLoopIdx, Constants.FlywheelConstants.kGains_Velocit.kI, Constants.FlywheelConstants.kTimeoutMs);
		Robot.flywheel.rightFlywheel.config_kD(Constants.FlywheelConstants.kPIDLoopIdx, Constants.FlywheelConstants.kGains_Velocit.kD, Constants.FlywheelConstants.kTimeoutMs);
   

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
  public void autonomousInit() {
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

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
