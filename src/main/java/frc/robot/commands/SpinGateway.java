/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Gateway;

public class SpinGateway extends CommandBase {
  
  Gateway m_gateway;
  DoubleSupplier m_speed;

  /**
   * Creates a new SpinIndexer.
   */
  public SpinGateway(Gateway gateway, DoubleSupplier speed) {
    m_gateway = gateway;
    m_speed = speed;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(gateway);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_gateway.spin(m_speed.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_gateway.spin(0);
  }
}
