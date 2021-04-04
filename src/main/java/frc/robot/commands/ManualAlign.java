// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class ManualAlign extends CommandBase {
  
  DoubleSupplier m_forward, m_turn;
  XboxController m_controller;
  Drivetrain m_drivetrain;
  Limelight m_limelight;
  
  /** Creates a new ManualAlign. */
  public ManualAlign(DoubleSupplier forward, DoubleSupplier turn, XboxController controller, Drivetrain drivetrain, Limelight limelight) {
    m_forward = forward;
    m_turn = turn;
    m_drivetrain = drivetrain;
    m_limelight = limelight;
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.pravshotDrive(m_forward.getAsDouble(), m_turn.getAsDouble());

    double offset = m_limelight.getXOffset();
    double rumble = Math.abs(m_limelight.getXOffset() / 30.0);
    
    if (offset > 1.0) {
      m_controller.setRumble(RumbleType.kLeftRumble, 0);
      m_controller.setRumble(RumbleType.kRightRumble, rumble);
    }
    else if (offset < -1.0) {
      m_controller.setRumble(RumbleType.kLeftRumble, rumble);
      m_controller.setRumble(RumbleType.kRightRumble, 0);
    }
    else
    {
      m_controller.setRumble(RumbleType.kLeftRumble, 0);
      m_controller.setRumble(RumbleType.kRightRumble, 0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_controller.setRumble(RumbleType.kLeftRumble, 0);
    m_controller.setRumble(RumbleType.kRightRumble, 0);
  }
}
