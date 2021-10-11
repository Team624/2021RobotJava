
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.NetworkTableEntry;

public class Hopper extends SubsystemBase {
  private final CANSparkMax leftMotor = new CANSparkMax(Constants.CAN.LeftHopperID, MotorType.kBrushless);
  private final CANSparkMax rightMotor = new CANSparkMax(Constants.CAN.RightHopperID, MotorType.kBrushless);

  private double hopperSpeed;
  private double dashHopperSpeed;
  private boolean useDash;

  private ShuffleboardTab hopperTab = Shuffleboard.getTab("Hopper");
  private NetworkTableEntry dashSpeed = hopperTab.add("Hopper Speed", 0).withPosition(0, 0).getEntry();
  private NetworkTableEntry dashSetHopperSpeed = hopperTab.add("Set Hopper Speed", false).withPosition(0, 1).getEntry();
  
  /** Creates a new Hopper. */
  public Hopper() {
    leftMotor.setInverted(Constants.HopperSettings.reverseLeftHopperMotor);
    rightMotor.setInverted(Constants.HopperSettings.reverseRightHopperMotor);
    hopperSpeed = Constants.HopperSettings.hopperSpeed;
  }

  @Override
  public void periodic() {
    hopperDash();
    // This method will be called once per scheduler run
  }

  public void hopperDash(){
    dashHopperSpeed = dashSpeed.getDouble(0);
    useDash = dashSetHopperSpeed.getBoolean(false);
  }

  public void updateHopperSpeed(){
    if(useDash){
      hopperSpeed = dashHopperSpeed;
    }
  }

  public void onHopper(){
    leftMotor.set(hopperSpeed);
    rightMotor.set(hopperSpeed);
  }

  public void reverseHopper(){
    leftMotor.set(-hopperSpeed);
    rightMotor.set(-hopperSpeed);
  }

  public void stopHopper(){
    leftMotor.stopMotor();;
    rightMotor.stopMotor();;
  }

}
