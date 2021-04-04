// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class PravshotDrive extends CommandBase {
  
  Drivetrain m_drivetrain;
  DoubleSupplier m_speed, m_turn;
  BooleanSupplier m_slow;
  
  /** Creates a new PravshotDrive. */
  public PravshotDrive(DoubleSupplier speed, DoubleSupplier turn, BooleanSupplier slow, Drivetrain drivetrain) {
    m_drivetrain = drivetrain;
    m_slow = slow;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double factor = m_slow.getAsBoolean() ? .6 : 1;
    double forward = m_speed.getAsDouble(), turn = m_turn.getAsDouble();

    m_drivetrain.pravshotDrive(forward * factor, turn * factor);
  }
}
