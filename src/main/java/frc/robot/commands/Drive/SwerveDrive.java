// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class SwerveDrive extends CommandBase {
  /** Creates a new SwerveDrive. */
  public SwerveDrive() {
    addRequirements(Robot.drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.drivetrain.setDrivePID(Robot.drivetrain.d_pidBackLeft);
    Robot.drivetrain.setDrivePID(Robot.drivetrain.d_pidBackRight);
    Robot.drivetrain.setDrivePID(Robot.drivetrain.d_pidFrontLeft);
    Robot.drivetrain.setDrivePID(Robot.drivetrain.d_pidFrontRight);

    Robot.drivetrain.setSteerPID(Robot.drivetrain.s_pidBackLeft);
    Robot.drivetrain.setSteerPID(Robot.drivetrain.s_pidBackRight);
    Robot.drivetrain.setSteerPID(Robot.drivetrain.s_pidFrontLeft);
    Robot.drivetrain.setSteerPID(Robot.drivetrain.s_pidFrontRight);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.drivetrain.updateSteerPID(Robot.drivetrain.s_pidBackLeft, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.drivetrain.stopMotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
