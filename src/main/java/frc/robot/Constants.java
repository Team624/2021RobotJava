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
        
        public static final int LeftHopper = 6;
        public static final int RightHopper = 9;
    }

    public static class OI{
        public static final int driverUSB = 0;
        public static final int manipulatorUSB = 1;
        public static final int LeftStickYID = 0;

        //no clue if this is accurate
        public static final int xButton = 3;
    }

}