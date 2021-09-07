// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.ArrayList;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

public class Flywheel extends SubsystemBase {
  public TalonFX leftFlywheel = new TalonFX(Constants.CAN.leftFlywheelID);
  public TalonFX rightFlywheel = new TalonFX(Constants.CAN.rightFlywheelID);
  public static Orchestra orchestra;

  /** Creates a new Flywheel. */
  public Flywheel() {
    leftFlywheel.setInverted(true);

    //music code
    ArrayList<TalonFX> instruments = new ArrayList<TalonFX>();
    instruments.add(leftFlywheel);
    instruments.add(rightFlywheel);
    System.out.println(instruments);
    orchestra = new Orchestra(instruments);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void stopFlywheel(TalonFXControlMode mode){
    leftFlywheel.set(mode, 0);
    rightFlywheel.set(mode, 0);
  }

  public void setFlywheel(TalonFXControlMode mode, double speed){
    leftFlywheel.set(mode, speed);
    rightFlywheel.set(mode, speed);
  }

  public void play(){
    orchestra.loadMusic("Candy.chrp");
    orchestra.play();
  }
}
