// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class SpinShooter extends CommandBase {
  
  Shooter m_shooter;
  DoubleSupplier m_rpm;

  /** Creates a new SpinShooter. */
  public SpinShooter(Shooter shooter, DoubleSupplier rpm) {
    m_shooter = shooter;
    m_rpm = rpm;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooter.setRPM(m_rpm.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.zero();
  }

  @Override
  public boolean isFinished() {
    // TODO Auto-generated method stub
    return false;
  }
}
