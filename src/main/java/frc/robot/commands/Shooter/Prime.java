// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class Prime extends CommandBase {
  /** Creates a new Prime. */
  public Prime() {
    addRequirements(Robot.shooter);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Robot.shooter.getPrime()){
      Robot.shooter.manualFlywheel(TalonFXControlMode.Velocity);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
<<<<<<< Updated upstream
}
=======
}
>>>>>>> Stashed changes
