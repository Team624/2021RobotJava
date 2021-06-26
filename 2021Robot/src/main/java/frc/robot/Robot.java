// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import frc.robot.Subsytems.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import com.ctre.phoenix.motorcontrol.*;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  
  public final Joystick d_stick = new Joystick(0);
  public final Joystick m_stick = new Joystick(1);
  private final Timer m_timer = new Timer();

  public Hopper hopper = new Hopper();
  public Intake intake = new Intake();
  public Shooter shooter = new Shooter();
  public Music music = new Music();

  @Override
  public void robotInit() {
    shooter.leftFlywheel.configFactoryDefault();
    shooter.rightFlywheel.configFactoryDefault();

    shooter.leftFlywheel.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    shooter.rightFlywheel.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

    shooter.leftFlywheel.setSensorPhase(true);
    shooter.rightFlywheel.setSensorPhase(true);

    shooter.leftFlywheel.configNominalOutputForward(0, Constants.kTimeoutMs);
		shooter.leftFlywheel.configNominalOutputReverse(0, Constants.kTimeoutMs);
		shooter.leftFlywheel.configPeakOutputForward(1, Constants.kTimeoutMs);
    shooter.leftFlywheel.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    
    shooter.rightFlywheel.configNominalOutputForward(0, Constants.kTimeoutMs);
		shooter.rightFlywheel.configNominalOutputReverse(0, Constants.kTimeoutMs);
		shooter.rightFlywheel.configPeakOutputForward(1, Constants.kTimeoutMs);
    shooter.rightFlywheel.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    
    shooter.leftFlywheel.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
		shooter.leftFlywheel.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
		shooter.leftFlywheel.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
    shooter.leftFlywheel.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);
    
    shooter.rightFlywheel.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
		shooter.rightFlywheel.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
		shooter.rightFlywheel.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
		shooter.rightFlywheel.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);
  }

  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }

  @Override
  public void autonomousPeriodic() {}
  
  @Override
  public void teleopInit() {
    music.assignInstruments(shooter);
  }

  @Override
  public void teleopPeriodic() {
    shooter.shooterLoop(m_stick, hopper, intake);
    hopper.hopperLoop(m_stick);
    intake.intakeLoop(m_stick);
    music.musicLoop(m_stick);
  }

}
