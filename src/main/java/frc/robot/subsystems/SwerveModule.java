// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Constants.ModuleConstants;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;

import java.lang.Math;

public class SwerveModule {
  private final CANSparkMax m_driveMotor;
  private final CANSparkMax m_turningMotor;

  private final CANEncoder m_driveEncoder;
  private final CANEncoder m_turningEncoder;

  private PIDController drivePIDController =
      new PIDController(Constants.PID.SwervePIDConstants.kPModuleDriveController, Constants.PID.SwervePIDConstants.kIModuleDriveController, Constants.PID.SwervePIDConstants.kDModuleDriveController);

  // Using a TrapezoidProfile PIDController to allow for smooth turning
  private ProfiledPIDController m_turningPIDController =
      new ProfiledPIDController(
          Constants.PID.SwervePIDConstants.kPModuleTurningController,
          Constants.PID.SwervePIDConstants.kIModuleTurningController,
          Constants.PID.SwervePIDConstants.kDModuleTurningController,
          new TrapezoidProfile.Constraints(
              ModuleConstants.kMaxModuleAngularSpeedRadiansPerSecond,
              ModuleConstants.kMaxModuleAngularAccelerationRadiansPerSecondSquared));

  /**
   * Constructs a SwerveModule.
   *
   * @param driveMotorChannel ID for the drive motor.
   * @param turningMotorChannel ID for the turning motor.
   */
  public SwerveModule(
      int driveMotorChannel,
      int turningMotorChannel,
      int[] driveEncoderPorts,
      int[] turningEncoderPorts,
      boolean driveEncoderReversed,
      boolean turningEncoderReversed) {

    m_driveMotor = new CANSparkMax(driveMotorChannel, MotorType.kBrushless);
    m_turningMotor = new CANSparkMax(turningMotorChannel, MotorType.kBrushless);
    m_driveMotor.setIdleMode(IdleMode.kBrake);
    m_turningMotor.setIdleMode(IdleMode.kBrake);

    this.m_driveEncoder = m_driveMotor.getEncoder();
    this.m_turningEncoder = m_turningMotor.getEncoder();

    // Set the distance per pulse for the drive encoder. We can simply use the
    // distance traveled for one rotation of the wheel divided by the encoder
    // resolution.

    // Set whether drive encoder should be reversed or not
    m_driveMotor.setInverted(driveEncoderReversed);

    // Set the distance (in this case, angle) per pulse for the turning encoder.
    // This is the the angle through an entire rotation (2 * wpi::math::pi)
    // divided by the encoder resolution.

    // Set whether turning encoder should be reversed or not
    m_turningMotor.setInverted(turningEncoderReversed);

    // Limit the PID Controller's input range between -pi and pi and set the input
    // to be continuous.
    m_turningPIDController.enableContinuousInput(-Math.PI, Math.PI);
  }

  /**
   * Returns the current state of the module.
   *
   * @return The current state of the module.
   */
  public SwerveModuleState getState() {
    return new SwerveModuleState(getDriveVelocity(), new Rotation2d(getTurnPosition()));
  }

  /**
   * Sets the desired state for the module.
   *
   * @param desiredState Desired state with speed and angle.
   */
  public void setDesiredState(SwerveModuleState desiredState, boolean freezeSpeed) {
    // Optimize the reference state to avoid spinning further than 90 degrees
    SwerveModuleState state =
        SwerveModuleState.optimize(desiredState, new Rotation2d(getTurnPosition()));
    // Calculate the drive output from the drive PID controller.
    final double driveOutput =
        drivePIDController.calculate(getDriveVelocity(), state.speedMetersPerSecond * Constants.PID.SwervePIDConstants.kFModuleDriveController);
    //System.out.println(desiredState.speedMetersPerSecond + " : " + getDriveVelocity());
    //System.out.println(desiredState.angle + " : " + getTurnPosition());
    // Calculate the turning motor output from the turning PID controller.
    final var turnOutput =
        m_turningPIDController.calculate(getTurnPosition(), state.angle.getRadians());

    // Calculate the turning motor output from the turning PID controller.
   //m_driveMotor.set(driveOutput);
   if(freezeSpeed == true){
    m_driveMotor.set(0); 
    m_turningMotor.set(turnOutput);
   }else{
    m_turningMotor.set(turnOutput);
    m_driveMotor.set(driveOutput);
   }
  }

  /** Zeros all the SwerveModule encoders. */
  public void resetEncoders() {
    m_driveEncoder.setPosition(0);
    m_turningEncoder.setPosition(0);
  }

  public double getDriveVelocity(){
    return (m_driveEncoder.getVelocity()/(ModuleConstants.driveMotorRatio * 60.0)) * ModuleConstants.kWheelDiameterMeters * Math.PI;
  }
  
  public double getEncoderVal(){
    return m_turningEncoder.getPosition();
  }

  public double getTurnPosition(){
    double angle = (m_turningEncoder.getPosition()/ModuleConstants.turningMotorRatio) * Math.PI * 2.0;
    // Between -2pi and 2pi
    angle = angle % (2*Math.PI);
    // Return between -pi and pi
    if (angle < -Math.PI)
      angle += (Math.PI * 2);
    else if (angle > Math.PI)
      angle -= (Math.PI * 2);
    
    return angle;
  }

  public void newDrivePID(double pVal, double iVal, double dVal){
    drivePIDController = new PIDController(pVal, iVal, dVal);
  }

  public void newSteerPID(double pVal, double iVal, double dVal){
    drivePIDController = new PIDController(pVal, iVal, dVal);
  }
}
