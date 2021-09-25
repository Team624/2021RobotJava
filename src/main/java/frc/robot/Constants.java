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
        public static final int FrontLeftDriveID = 14;
	    public static final int FrontLeftSteerID = 15;
	    public static final int FrontRightDriveID = 16;
        public static final int FrontRightSteerID = 1;
        public static final int BackLeftDriveID = 2;
	    public static final int BackLeftSteerID = 3;
	    public static final int BackRightDriveID = 13;
        public static final int BackRightSteerID = 12;
        
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

        public static class SwerveConstants{
            public static final double d_kP = 0.00015;
            public static final double d_kI = 0;
            public static final double d_kD = 0;
            public static final double d_kIz = 0;
            public static final double d_kFF = .000168;
            public static final double d_kMaxOutput = 1;
            public static final double d_kMinOutput = -1;
            public static final int d_maxRPM = 5700;
            public static final int d_maxVel = 2000;
            public static final int d_maxAcc = 1500;
    
            // PIDs for the steer motors
            public static final double s_kP = 1;
            public static final double s_kI = 0;
            public static final double s_kD = 0;
            public static final double s_kIz = 0;
            public static final double s_kFF = 0;
            public static final double s_kMaxOutput = 1;
            public static final double s_kMinOutput = -1;
            public static final int s_maxRPM = 5700;
            public static final int s_maxVel = 2000;
            public static final int s_maxAcc = 1500;
        }
        
    }

    // COPY
    public static final class DriveConstants {
        public static final int kFrontLeftDriveMotorPort = 0;
        public static final int kRearLeftDriveMotorPort = 2;
        public static final int kFrontRightDriveMotorPort = 4;
        public static final int kRearRightDriveMotorPort = 6;
    
        public static final int kFrontLeftTurningMotorPort = 1;
        public static final int kRearLeftTurningMotorPort = 3;
        public static final int kFrontRightTurningMotorPort = 5;
        public static final int kRearRightTurningMotorPort = 7;
    
        public static final int[] kFrontLeftTurningEncoderPorts = new int[] {0, 1};
        public static final int[] kRearLeftTurningEncoderPorts = new int[] {2, 3};
        public static final int[] kFrontRightTurningEncoderPorts = new int[] {4, 5};
        public static final int[] kRearRightTurningEncoderPorts = new int[] {5, 6};
    
        public static final boolean kFrontLeftTurningEncoderReversed = false;
        public static final boolean kRearLeftTurningEncoderReversed = true;
        public static final boolean kFrontRightTurningEncoderReversed = false;
        public static final boolean kRearRightTurningEncoderReversed = true;
    
        public static final int[] kFrontLeftDriveEncoderPorts = new int[] {7, 8};
        public static final int[] kRearLeftDriveEncoderPorts = new int[] {9, 10};
        public static final int[] kFrontRightDriveEncoderPorts = new int[] {11, 12};
        public static final int[] kRearRightDriveEncoderPorts = new int[] {13, 14};
    
        public static final boolean kFrontLeftDriveEncoderReversed = false;
        public static final boolean kRearLeftDriveEncoderReversed = true;
        public static final boolean kFrontRightDriveEncoderReversed = false;
        public static final boolean kRearRightDriveEncoderReversed = true;
    
        public static final double kTrackWidth = 0.5;
        // Distance between centers of right and left wheels on robot
        public static final double kWheelBase = 0.7;
        // Distance between front and back wheels on robot
        public static final SwerveDriveKinematics kDriveKinematics =
            new SwerveDriveKinematics(
                new Translation2d(kWheelBase / 2, kTrackWidth / 2),
                new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
                new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
                new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));
    
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
        public static final double kWheelDiameterMeters = 0.15;
        public static final double kDriveEncoderDistancePerPulse =
            // Assumes the encoders are directly mounted on the wheel shafts
            (kWheelDiameterMeters * Math.PI) / (double) kEncoderCPR;
    
        public static final double kTurningEncoderDistancePerPulse =
            // Assumes the encoders are on a 1:1 reduction with the module shaft.
            (2 * Math.PI) / (double) kEncoderCPR;
    
        public static final double kPModuleTurningController = 1;
    
        public static final double kPModuleDriveController = 1;
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

}
