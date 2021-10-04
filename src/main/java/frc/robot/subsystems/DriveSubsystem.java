// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

@SuppressWarnings("PMD.ExcessiveImports")
public class DriveSubsystem extends SubsystemBase {
  private double drivePconstant;
  private double driveIconstant;
  private double driveDconstant;

  private double steerPconstant;
  private double steerIconstant;
  private double steerDconstant;

  private boolean tuneDrive;
  private boolean tuneSteer;

  private ShuffleboardTab driveTab = Shuffleboard.getTab("Drive");

  private NetworkTableEntry dashTuneDrivePid = driveTab.add("Tune Drive PID", false).withPosition(0, 0).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
  private NetworkTableEntry DRIVE_PID_P = driveTab.addPersistent("PID Drive P", Constants.PID.SwervePIDConstants.kPModuleDriveController).withPosition(0, 1).getEntry();
  private NetworkTableEntry DRIVE_PID_I = driveTab.addPersistent("PID Drive I", Constants.PID.SwervePIDConstants.kIModuleDriveController).withPosition(0, 2).getEntry();
  private NetworkTableEntry DRIVE_PID_D = driveTab.addPersistent("PID Drive D", Constants.PID.SwervePIDConstants.kDModuleDriveController).withPosition(0, 3).getEntry();

  private NetworkTableEntry dashTuneSteerPid = driveTab.add("Tune Steer PID", false).withPosition(1, 0).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
  private NetworkTableEntry STEER_PID_P = driveTab.addPersistent("PID Steer P", Constants.PID.SwervePIDConstants.kPModuleTurningController).withPosition(1, 1).getEntry();
  private NetworkTableEntry STEER_PID_I = driveTab.addPersistent("PID Steer I", Constants.PID.SwervePIDConstants.kIModuleTurningController).withPosition(1, 2).getEntry();
  private NetworkTableEntry STEER_PID_D = driveTab.addPersistent("PID Steer D", Constants.PID.SwervePIDConstants.kDModuleTurningController).withPosition(1, 3).getEntry();

  private NetworkTableEntry dashCurrentAngle = driveTab.add("Current Angle", 0).withPosition(2, 0).getEntry();

  // Robot swerve modules
  private final SwerveModule m_frontLeft =
      new SwerveModule(
          DriveConstants.kFrontLeftDriveMotorPort,
          DriveConstants.kFrontLeftTurningMotorPort,
          DriveConstants.kFrontLeftDriveEncoderPorts,
          DriveConstants.kFrontLeftTurningEncoderPorts,
          DriveConstants.kFrontLeftDriveEncoderReversed,
          DriveConstants.kFrontLeftTurningEncoderReversed);

  private final SwerveModule m_rearLeft =
      new SwerveModule(
          DriveConstants.kRearLeftDriveMotorPort,
          DriveConstants.kRearLeftTurningMotorPort,
          DriveConstants.kRearLeftDriveEncoderPorts,
          DriveConstants.kRearLeftTurningEncoderPorts,
          DriveConstants.kRearLeftDriveEncoderReversed,
          DriveConstants.kRearLeftTurningEncoderReversed);

  private final SwerveModule m_frontRight =
      new SwerveModule(
          DriveConstants.kFrontRightDriveMotorPort,
          DriveConstants.kFrontRightTurningMotorPort,
          DriveConstants.kFrontRightDriveEncoderPorts,
          DriveConstants.kFrontRightTurningEncoderPorts,
          DriveConstants.kFrontRightDriveEncoderReversed,
          DriveConstants.kFrontRightTurningEncoderReversed);

  private final SwerveModule m_rearRight =
      new SwerveModule(
          DriveConstants.kRearRightDriveMotorPort,
          DriveConstants.kRearRightTurningMotorPort,
          DriveConstants.kRearRightDriveEncoderPorts,
          DriveConstants.kRearRightTurningEncoderPorts,
          DriveConstants.kRearRightDriveEncoderReversed,
          DriveConstants.kRearRightTurningEncoderReversed);

  // The gyro sensor
  private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);

  // Odometry class for tracking robot pose
  SwerveDriveOdometry m_odometry =
      new SwerveDriveOdometry(DriveConstants.kDriveKinematics, m_gyro.getRotation2d());

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {}

  @Override
  public void periodic() {
    driveDash();
    updatePID();
    // Update the odometry in the periodic block
    m_odometry.update(
        m_gyro.getRotation2d(),
        m_frontLeft.getState(),
        m_rearLeft.getState(),
        m_frontRight.getState(),
        m_rearRight.getState());
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
    var swerveModuleStates =
        DriveConstants.kDriveKinematics.toSwerveModuleStates(
            fieldRelative
                ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, m_gyro.getRotation2d())
                : new ChassisSpeeds(xSpeed, ySpeed, rot));
    SwerveDriveKinematics.normalizeWheelSpeeds(
        swerveModuleStates, DriveConstants.kMaxSpeedMetersPerSecond);
    //System.out.println("Front module: "+ m_frontLeft.getEncoderVal() + ", " + swerveModuleStates[0].toString());
    //System.out.println("Back module: " + m_rearLeft.getEncoderVal() + ", " + swerveModuleStates[2].toString());
    m_frontLeft.setDesiredState(swerveModuleStates[0]);
    m_frontRight.setDesiredState(swerveModuleStates[1]);
    m_rearLeft.setDesiredState(swerveModuleStates[2]);
    m_rearRight.setDesiredState(swerveModuleStates[3]);
  }

  /**
   * Sets the swerve ModuleStates.
   *
   * @param desiredStates The desired SwerveModule states.
   */
  public void setModuleStates(SwerveModuleState[] desiredStates) {
    SwerveDriveKinematics.normalizeWheelSpeeds(
        desiredStates, DriveConstants.kMaxSpeedMetersPerSecond);
    m_frontLeft.setDesiredState(desiredStates[0]);
    m_frontRight.setDesiredState(desiredStates[1]);
    m_rearLeft.setDesiredState(desiredStates[2]);
    m_rearRight.setDesiredState(desiredStates[3]);
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
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    return m_gyro.getRate() * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
  }

  public void driveDash(){
    dashCurrentAngle.setDouble(m_gyro.getYaw());

    tuneDrive = dashTuneDrivePid.getBoolean(false);
    tuneSteer = dashTuneSteerPid.getBoolean(false);

    drivePconstant = DRIVE_PID_P.getDouble(0);
    driveIconstant = DRIVE_PID_I.getDouble(0);
    driveDconstant = DRIVE_PID_D.getDouble(0);

    steerPconstant = STEER_PID_P.getDouble(0);
    steerIconstant = STEER_PID_I.getDouble(0);
    steerDconstant = STEER_PID_D.getDouble(0);
  }

  public void updatePID(){
    if(tuneDrive == true){
      m_frontLeft.newDrivePID(drivePconstant, driveIconstant, driveDconstant);
      m_rearLeft.newDrivePID(drivePconstant, driveIconstant, driveDconstant);
      m_rearRight.newDrivePID(drivePconstant, driveIconstant, driveDconstant);
      m_frontRight.newDrivePID(drivePconstant, driveIconstant, driveDconstant);
    }
    if(tuneSteer == true){
      m_frontLeft.newSteerPID(steerPconstant, steerIconstant, steerDconstant);
      m_rearLeft.newSteerPID(steerPconstant, steerIconstant, steerDconstant);
      m_rearRight.newSteerPID(steerPconstant, steerIconstant, steerDconstant);
      m_frontRight.newSteerPID(steerPconstant, steerIconstant, steerDconstant);
    }
  }
}
