// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class AutoAdjustShooter extends CommandBase {
  
  Shooter m_shooter;
  Limelight m_limelight;

  /** Creates a new AutoAdjustShooter. */
  public AutoAdjustShooter(Shooter shooter, Limelight limelight) {
    m_shooter = shooter;
    m_limelight = limelight;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double distance = m_limelight.getDistance();
    boolean shouldBeRaised = m_shooter.shouldBeRaised(distance);

    if(shouldBeRaised && !m_shooter.isRaised())
      m_shooter.raise();
    // else if(!shouldBeRaised && m_shooter.isRaised())
    //   m_shooter.lower();
  }
}
