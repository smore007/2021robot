// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Gateway;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class Shoot extends ParallelCommandGroup {
  
  final double kAcceptableShooterRPMError = 100;
  
  /** Creates a new WarmUpShooter. */
  public Shoot(Shooter shooter, Gateway gateway, Indexer indexer, Limelight limelight, DoubleSupplier rpmTarget) {
    addCommands(
      new SpinShooter(shooter, rpmTarget),
      new RunCommand(() -> {
        if(Math.abs(shooter.getClosedLoopErrorRPM()) < kAcceptableShooterRPMError) {
          gateway.spin(.5);
          indexer.spin(-.3);
        } else {
          gateway.spin(0);
          indexer.spin(0);
        }
      }, gateway, indexer)
    );
  }

  public Shoot(Shooter shooter, Gateway gateway, Indexer indexer, Limelight limelight) {
    this(shooter, gateway, indexer, limelight, () -> shooter.getXuru((limelight.getDistance())));
  }
}
