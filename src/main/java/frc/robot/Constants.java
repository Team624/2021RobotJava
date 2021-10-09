// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


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
        public static final int driverUSB = 0;
        public static final int manipulatorUSB = 1;

        public static final int leftStickYID = 0;
        public static final int pressRightStickID = 10;
        public static final int pressLeftStickID = 9;
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

}
