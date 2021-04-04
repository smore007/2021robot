/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;

public class SpinIndexer extends CommandBase {
  
  Indexer m_indexer;
  DoubleSupplier m_speed;
  double m_idleSpeed;

  /**
   * Creates a new SpinIndexer.
   */
  public SpinIndexer(Indexer indexer, DoubleSupplier speed, double idleSpeed) {
    m_indexer = indexer;
    m_speed = speed;
    m_idleSpeed = idleSpeed;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(indexer);
  }

  public SpinIndexer(Indexer indexer, DoubleSupplier speed) {
    this(indexer, speed, 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = m_speed.getAsDouble();    
    if(Math.abs(speed) < m_idleSpeed)
      speed = m_idleSpeed;
    m_indexer.spin(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_indexer.spin(0);
  }
}
