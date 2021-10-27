// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Robot;
import frc.robot.Constants;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;

import com.analog.adis16470.frc.ADIS16470_IMU;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

@SuppressWarnings("PMD.ExcessiveImports")
public class DriveSubsystem extends SubsystemBase {
  private double drivePconstant;
  private double driveIconstant;
  private double driveDconstant;
  private double driveFconstant;

  private double steerPconstant;
  private double steerIconstant;
  private double steerDconstant;

  private boolean tuneDrive;
  private boolean tuneSteer;

  private double driveMultiplier;
  private double turnMultiplier;

  private boolean freezeDrive = false;

  public boolean resetGyro = false;

  private boolean updatedDrivePIDAlready = false;
  private boolean updatedSteerPIDAlready = false;

  private ShuffleboardTab driveTab = Shuffleboard.getTab("Drive");

  private NetworkTableEntry dashTuneDrivePid = driveTab.add("Tune Drive PID", false).withPosition(0, 0).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
  private NetworkTableEntry DRIVE_PID_P = driveTab.addPersistent("PID Drive P", Constants.PID.SwervePIDConstants.kPModuleDriveController).withPosition(0, 1).getEntry();
  private NetworkTableEntry DRIVE_PID_I = driveTab.addPersistent("PID Drive I", Constants.PID.SwervePIDConstants.kIModuleDriveController).withPosition(0, 2).getEntry();
  private NetworkTableEntry DRIVE_PID_D = driveTab.addPersistent("PID Drive D", Constants.PID.SwervePIDConstants.kDModuleDriveController).withPosition(0, 3).getEntry();
  private NetworkTableEntry DRIVE_PID_F = driveTab.addPersistent("PID Drive F", Constants.PID.SwervePIDConstants.kDModuleDriveController).withPosition(0, 4).getEntry();

  private NetworkTableEntry dashTuneSteerPid = driveTab.add("Tune Steer PID", false).withPosition(1, 0).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
  private NetworkTableEntry STEER_PID_P = driveTab.addPersistent("PID Steer P", Constants.PID.SwervePIDConstants.kPModuleTurningController).withPosition(1, 1).getEntry();
  private NetworkTableEntry STEER_PID_I = driveTab.addPersistent("PID Steer I", Constants.PID.SwervePIDConstants.kIModuleTurningController).withPosition(1, 2).getEntry();
  private NetworkTableEntry STEER_PID_D = driveTab.addPersistent("PID Steer D", Constants.PID.SwervePIDConstants.kDModuleTurningController).withPosition(1, 3).getEntry();

  private NetworkTableEntry translationMult = driveTab.addPersistent("Translation Speed", Constants.DriveConstants.translationMultiplier).withPosition(2, 0).getEntry();
  private NetworkTableEntry rotationMult = driveTab.addPersistent("Rotation Speed", Constants.DriveConstants.rotationMultipler).withPosition(2, 1).getEntry();

  private NetworkTableEntry dashCurrentAngle = driveTab.add("Current Angle", 0).withPosition(2, 2).getEntry();

  // The gyro sensor
  public static final ADIS16470_IMU m_gyro = new ADIS16470_IMU();

  private SwerveModuleState[] lStates = {
    new SwerveModuleState(), 
    new SwerveModuleState(), 
    new SwerveModuleState(), 
    new SwerveModuleState()
  };

  // Robot swerve modules
  private final SwerveModule m_frontLeft =
      new SwerveModule(
          Constants.CAN.kFrontLeftDriveMotorPort,
          Constants.CAN.kFrontLeftTurningMotorPort,
          Constants.DriveConstants.kFrontLeftDriveEncoderPorts,
          Constants.DriveConstants.kFrontLeftTurningEncoderPorts,
          Constants.DriveConstants.kFrontLeftDriveEncoderReversed,
          Constants.DriveConstants.kFrontLeftTurningEncoderReversed);

  private final SwerveModule m_rearLeft =
      new SwerveModule(
          Constants.CAN.kRearLeftDriveMotorPort,
          Constants.CAN.kRearLeftTurningMotorPort,
          Constants.DriveConstants.kRearLeftDriveEncoderPorts,
          Constants.DriveConstants.kRearLeftTurningEncoderPorts,
          Constants.DriveConstants.kRearLeftDriveEncoderReversed,
          Constants.DriveConstants.kRearLeftTurningEncoderReversed);

  private final SwerveModule m_frontRight =
      new SwerveModule(
          Constants.CAN.kFrontRightDriveMotorPort,
          Constants.CAN.kFrontRightTurningMotorPort,
          Constants.DriveConstants.kFrontRightDriveEncoderPorts,
          Constants.DriveConstants.kFrontRightTurningEncoderPorts,
          Constants.DriveConstants.kFrontRightDriveEncoderReversed,
          Constants.DriveConstants.kFrontRightTurningEncoderReversed);

  private final SwerveModule m_rearRight =
      new SwerveModule(
          Constants.CAN.kRearRightDriveMotorPort,
          Constants.CAN.kRearRightTurningMotorPort,
          Constants.DriveConstants.kRearRightDriveEncoderPorts,
          Constants.DriveConstants.kRearRightTurningEncoderPorts,
          Constants.DriveConstants.kRearRightDriveEncoderReversed,
          Constants.DriveConstants.kRearRightTurningEncoderReversed);

  // Odometry class for tracking robot pose
  SwerveDriveOdometry m_odometry =
      new SwerveDriveOdometry(Constants.DriveConstants.kDriveKinematics, m_gyro.getRotation2d());

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {}

  @Override
  public void periodic() {
    resetGyro();
    driveDash();
    //updatePID();

    // Update the odometry in the periodic block
    m_odometry.update(
        m_gyro.getRotation2d(),
        m_frontLeft.getState(),
        m_rearLeft.getState(),
        m_frontRight.getState(),
        m_rearRight.getState());
  }

  public void resetGyro(){
    if(Robot.m_robotContainer.getDriverButton(Constants.OI.leftBumperID)){
      m_gyro.reset();
    }
  }

  public void driveDash(){
    dashCurrentAngle.setDouble(m_gyro.getAngle());

    tuneDrive = dashTuneDrivePid.getBoolean(false);
    tuneSteer = dashTuneSteerPid.getBoolean(false);

    drivePconstant = DRIVE_PID_P.getDouble(0);
    driveIconstant = DRIVE_PID_I.getDouble(0);
    driveDconstant = DRIVE_PID_D.getDouble(0);
    driveFconstant = DRIVE_PID_F.getDouble(0);

    steerPconstant = STEER_PID_P.getDouble(0);
    steerIconstant = STEER_PID_I.getDouble(0);
    steerDconstant = STEER_PID_D.getDouble(0);

    driveMultiplier = translationMult.getDouble(Constants.DriveConstants.translationMultiplier);
    turnMultiplier = rotationMult.getDouble(Constants.DriveConstants.rotationMultipler);
  }

  public void updatePID(){
    if(tuneDrive == true && updatedDrivePIDAlready == false){
      m_frontLeft.newDrivePID(drivePconstant, driveIconstant, driveDconstant);
      m_rearLeft.newDrivePID(drivePconstant, driveIconstant, driveDconstant);
      m_rearRight.newDrivePID(drivePconstant, driveIconstant, driveDconstant);
      m_frontRight.newDrivePID(drivePconstant, driveIconstant, driveDconstant);
      updatedDrivePIDAlready = true;
    }
    if(tuneSteer == true && updatedSteerPIDAlready == false){
      m_frontLeft.newSteerPID(steerPconstant, steerIconstant, steerDconstant);
      m_rearLeft.newSteerPID(steerPconstant, steerIconstant, steerDconstant);
      m_rearRight.newSteerPID(steerPconstant, steerIconstant, steerDconstant);
      m_frontRight.newSteerPID(steerPconstant, steerIconstant, steerDconstant);
      updatedSteerPIDAlready = true;
    }
  }

  /**
   * Method to drive the robot using joystick info.
   *
   * @param xSpeed Speed of the robot in the x direction (forward).
   * @param ySpeed Speed of the robot in the y direction (sideways).
   * @param rot Angular rate of the robot.
   * @param fieldRelative Whether the provided x and y speeds are relative to the field.
   */
  @SuppressWarnings("ParameterName")
  public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
    //System.out.println(xSpeed + " - " + ySpeed + " - " + rot);
    //System.out.println(m_frontLeft.getEncoderVal());
    //System.out.println("Front: " + m_frontLeft.getEncoderVal());
    //System.out.println("Back: " + m_rearLeft.getEncoderVal());
    xSpeed *= driveMultiplier;
    ySpeed *= driveMultiplier;
    rot *= turnMultiplier;

    var swerveModuleStates =
        Constants.DriveConstants.kDriveKinematics.toSwerveModuleStates(
            fieldRelative
                ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, m_gyro.getRotation2d())
                : new ChassisSpeeds(xSpeed, ySpeed, rot));
    SwerveDriveKinematics.normalizeWheelSpeeds(
        swerveModuleStates, Constants.DriveConstants.kMaxSpeedMetersPerSecond);
    //System.out.println("Front module: "+ m_frontLeft.getEncoderVal() + ", " + swerveModuleStates[0].toString());
    //System.out.println("Back module: " + m_rearLeft.getEncoderVal() + ", " + swerveModuleStates[2].toString());
    System.out.println("Current: " + m_frontLeft.getDriveVelocity() + " : " + swerveModuleStates[0].speedMetersPerSecond);
    if(Math.abs(xSpeed) + Math.abs(ySpeed) + Math.abs(rot) < Constants.DriveConstants.swerveThreshold){
      swerveModuleStates[0].speedMetersPerSecond = 0;
      swerveModuleStates[0].angle = lStates[0].angle;
      swerveModuleStates[1].speedMetersPerSecond = 0;
      swerveModuleStates[1].angle = lStates[1].angle;

      swerveModuleStates[2].speedMetersPerSecond = 0;
      swerveModuleStates[2].angle = lStates[2].angle;
      swerveModuleStates[3].speedMetersPerSecond = 0;
      swerveModuleStates[3].angle = lStates[3].angle;

      freezeDrive = true;
    }
    else{
      lStates = swerveModuleStates;
      freezeDrive = false;
    }

    m_frontLeft.setDesiredState(swerveModuleStates[0], freezeDrive);
    m_frontRight.setDesiredState(swerveModuleStates[1], freezeDrive);
    m_rearLeft.setDesiredState(swerveModuleStates[2], freezeDrive);
    m_rearRight.setDesiredState(swerveModuleStates[3], freezeDrive);    

  }

  /** Resets the drive encoders to currently read a position of 0. */
  public void resetEncoders() {
    m_frontLeft.resetEncoders();
    m_rearLeft.resetEncoders();
    m_frontRight.resetEncoders();
    m_rearRight.resetEncoders();
  }

  /** Zeroes the heading of the robot. */
  public void zeroHeading() {
    m_gyro.reset();
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in radians, from -pi to pi
   */
  public double getHeading() {
    return m_gyro.getRotation2d().getRadians();
  }

    /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
    m_odometry.resetPosition(pose, m_gyro.getRotation2d());
  }

  public double getFTerm(){
    if(driveFconstant == 0){
      return 1;
    }
    return driveFconstant;
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    return m_gyro.getRate() * (Constants.DriveConstants.kGyroReversed ? -1.0 : 1.0);
  }

  
}
