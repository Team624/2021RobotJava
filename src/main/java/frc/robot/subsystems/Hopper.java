
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import frc.robot.Constants;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Hopper extends SubsystemBase {
  private final CANSparkMax leftMotor = new CANSparkMax(Constants.CAN.LeftHopperID, MotorType.kBrushless);
  private final CANSparkMax rightMotor = new CANSparkMax(Constants.CAN.RightHopperID, MotorType.kBrushless);
  /** Creates a new Hopper. */
  public Hopper() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setHopper(double speed){
    leftMotor.set(speed);
    rightMotor.set(-speed);
  }

  public void stopHopper(){
    leftMotor.set(0);
    rightMotor.set(0);
  }
}
