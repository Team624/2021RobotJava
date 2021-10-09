// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static class CAN{
        public static final int LeftHopperID = 6;
        public static final int RightHopperID = 9;

        public static final int leftFlywheelID = 7;
        public static final int rightFlywheelID = 8;

        public static final int intakeMotorID = 11;
    }

    public static class Solenoid{
        public static final int intakeID = 0;
        public static final int hoodID = 1;
    }

    public static class OI{
        //BUTTON INDEXING STARTS AT 1 IN JAVA
        public static final int driverUSB = 0;
        public static final int manipulatorUSB = 1;

        public static final int LeftStickYID = 0;

        public static final int yButtonID = 4;
        public static final int xButtonID = 3;
        public static final int leftBumperID = 5;
    }

    public static class PID{

        public static class FlywheelConstants{
            public static final int kSlotIdx = 0;
            public static final int kPIDLoopIdx = 0;
            public static final int kTimeoutMs = 30;
            public final static Gains kGains_Velocit  = new Gains( 0.3, 0.0001, 0, 0.05,  10,  1.00);
            
        }

        public static class SwervePIDConstants{
            public static final double kPModuleTurningController = 0.3;
            public static final double kIModuleTurningController = 0.2;
            public static final double kDModuleTurningController = 0;
        
            public static final double kPModuleDriveController = 0.25;
            public static final double kIModuleDriveController = 0.5;
            public static final double kDModuleDriveController = 0.0;
        }
        
    }

    // COPY
    public static final class DriveConstants {
        public static final int kFrontLeftDriveMotorPort = 16;
        public static final int kRearLeftDriveMotorPort = 2;
        public static final int kFrontRightDriveMotorPort = 14;
        public static final int kRearRightDriveMotorPort = 13;
    
        public static final int kFrontLeftTurningMotorPort = 1;
        public static final int kRearLeftTurningMotorPort = 3;
        public static final int kFrontRightTurningMotorPort = 15;
        public static final int kRearRightTurningMotorPort = 12;
    
        public static final int[] kFrontLeftTurningEncoderPorts = new int[] {0, 1};
        public static final int[] kRearLeftTurningEncoderPorts = new int[] {2, 3};
        public static final int[] kFrontRightTurningEncoderPorts = new int[] {4, 5};
        public static final int[] kRearRightTurningEncoderPorts = new int[] {5, 6};
    
        public static final boolean kFrontLeftTurningEncoderReversed = true;
        public static final boolean kRearLeftTurningEncoderReversed = true;
        public static final boolean kFrontRightTurningEncoderReversed = true;
        public static final boolean kRearRightTurningEncoderReversed = true;
    
        public static final int[] kFrontLeftDriveEncoderPorts = new int[] {7, 8};
        public static final int[] kRearLeftDriveEncoderPorts = new int[] {9, 10};
        public static final int[] kFrontRightDriveEncoderPorts = new int[] {11, 12};
        public static final int[] kRearRightDriveEncoderPorts = new int[] {13, 14};
    
        public static final boolean kFrontLeftDriveEncoderReversed = false;
        public static final boolean kRearLeftDriveEncoderReversed = false;
        public static final boolean kFrontRightDriveEncoderReversed = false;
        public static final boolean kRearRightDriveEncoderReversed = false;
    
        public static final double swerveThreshold = .1;

        public static final double kTrackWidth = 1.0;
        // Distance between centers of right and left wheels on robot
        public static final double kWheelBase = 1.0;
        // Distance between front and back wheels on robot
        public static final SwerveDriveKinematics kDriveKinematics =
            new SwerveDriveKinematics(
                new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
                new Translation2d(kWheelBase / 2, kTrackWidth / 2),
                new Translation2d(-kWheelBase / 2, -kTrackWidth / 2),
                new Translation2d(-kWheelBase / 2, kTrackWidth / 2));
    
        public static final boolean kGyroReversed = false;
    
        // These are example values only - DO NOT USE THESE FOR YOUR OWN ROBOT!
        // These characterization values MUST be determined either experimentally or theoretically
        // for *your* robot's drive.
        // The RobotPy Characterization Toolsuite provides a convenient tool for obtaining these
        // values for your robot.
        public static final double ksVolts = 1;
        public static final double kvVoltSecondsPerMeter = 0.8;
        public static final double kaVoltSecondsSquaredPerMeter = 0.15;
    
        public static final double kMaxSpeedMetersPerSecond = 3;
    }

    // COPY
    public static final class ModuleConstants {
        public static final double kMaxModuleAngularSpeedRadiansPerSecond = 2 * Math.PI;
        public static final double kMaxModuleAngularAccelerationRadiansPerSecondSquared = 2 * Math.PI;
    
        public static final int kEncoderCPR = 1024;
        public static final double kWheelDiameterMeters = 0.1016;

        public static final double turningMotorRatio = 40.0;
        public static final double driveMotorRatio = 6.0;

        public static final double kDriveEncoderDistancePerPulse =
            // Assumes the encoders are directly mounted on the wheel shafts
            (kWheelDiameterMeters * Math.PI) / (double) kEncoderCPR;
    
        public static final double kTurningEncoderDistancePerPulse =
            // Assumes the encoders are on a 1:1 reduction with the module shaft.
            (2 * Math.PI) / (double) kEncoderCPR;
    }

    // COPY
    public static final class AutoConstants {
        public static final double kMaxSpeedMetersPerSecond = 3;
        public static final double kMaxAccelerationMetersPerSecondSquared = 3;
        public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
        public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;
    
        public static final double kPXController = 1;
        public static final double kPYController = 1;
        public static final double kPThetaController = 1;
    
        // Constraint for the motion profilied robot angle controller
        public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
            new TrapezoidProfile.Constraints(
                kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
    }

    public static final class TrackingConstants {
        public static final double kPTrackingController = 0;
    }

}
