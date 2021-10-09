// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.networktables.NetworkTableEntry;

public class Climber extends SubsystemBase {
    private final CANSparkMax theMotor = new CANSparkMax(Constants.CAN.LeftHopperID, MotorType.kBrushless);

    private double climbSpeed;

    private ShuffleboardTab hopperTab = Shuffleboard.getTab("Hopper");

    private NetworkTableEntry dashSetClimbSpeed = hopperTab.addPersistent("Set Speed", 0).getEntry();

    /** Creates a new Climber. */
    public Climber() {}

    @Override
    public void periodic() {
      climbDash();
      // This method will be called once per scheduler run
    }
  
    public void climbDash(){
      climbSpeed = dashSetClimbSpeed.getDouble(0);
    }
  
    public void forwardClimb(){
      theMotor.set(climbSpeed);
    }

    public void reverseClimb(){
    theMotor.set(-climbSpeed);
    }
  
    public void stopClimb(){
      theMotor.set(0);
    }
  
    public double getClimbSpeed(){
      return climbSpeed;
    }
}
