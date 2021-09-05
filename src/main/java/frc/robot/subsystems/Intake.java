// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  public TalonSRX intakeMotor = new TalonSRX(Constants.CAN.intakeMotorID);
  /** Creates a new Intake. */
  public Intake() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setIntake(double speed){
    intakeMotor.set(TalonSRXControlMode.PercentOutput, speed);
  }

  public void stopIntake(){
    intakeMotor.set(TalonSRXControlMode.PercentOutput, 0);
  }
}
