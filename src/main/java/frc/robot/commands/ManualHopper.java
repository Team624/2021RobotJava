
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;


public class ManualHopper extends CommandBase {
  //boolean xDown = Robot.m_robotContainer.getManipulatorButton(Constants.OI.xButton);
  /** Creates a new ManualHopper. */
  public ManualHopper() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.hopper);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.hopper.setHopper(.5);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(0 == 1){
      return false;
    }else{
      return true;
    }
  }
}