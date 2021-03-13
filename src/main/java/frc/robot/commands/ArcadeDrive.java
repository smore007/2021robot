// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {
  Drivetrain m_drivetrain;
  DoubleSupplier m_speed, m_turn;
  /** Creates a new ArcadeDrive. */
  public ArcadeDrive(DoubleSupplier speed, DoubleSupplier turn, Drivetrain drivetrain) {
    m_drivetrain = drivetrain;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.arcadeDrive(m_speed.getAsDouble(), m_turn.getAsDouble());
  }
}
