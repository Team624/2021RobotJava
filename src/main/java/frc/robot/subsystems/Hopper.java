
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import frc.robot.Constants;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.NetworkTableEntry;

public class Hopper extends SubsystemBase {
  private final CANSparkMax leftMotor = new CANSparkMax(Constants.CAN.LeftHopperID, MotorType.kBrushless);
  private final CANSparkMax rightMotor = new CANSparkMax(Constants.CAN.RightHopperID, MotorType.kBrushless);

  private double hopperSpeed;

  private ShuffleboardTab hopperTab = Shuffleboard.getTab("Hopper");

  private NetworkTableEntry dashSetHopperSpeed = hopperTab.addPersistent("Set Speed", 0).getEntry();
  /** Creates a new Hopper. */
  public Hopper() {}

  @Override
  public void periodic() {
    rightMotor.setInverted(true);
    hopperDash();
    // This method will be called once per scheduler run
  }

  public void hopperDash(){
    hopperSpeed = dashSetHopperSpeed.getDouble(0);
  }

  public void onHopper(){
    leftMotor.set(hopperSpeed);
    rightMotor.set(hopperSpeed);
  }

  public void stopHopper(){
    leftMotor.set(0);
    rightMotor.set(0);
  }

  public void forwardHopper(){
    leftMotor.set(hopperSpeed);
    rightMotor.set(hopperSpeed);
    //rightMotor.setInverted(true);
  }

  public void reverseHopper(){
    leftMotor.set(hopperSpeed);
    rightMotor.set(hopperSpeed);
    //rightMotor.setInverted(true);
  }

}
