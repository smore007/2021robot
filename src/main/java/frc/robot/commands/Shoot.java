// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.Gateway;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class Shoot extends ParallelCommandGroup {
  
  final double kAcceptableShooterRPMError = 100;
  
  /** Creates a new WarmUpShooter. */
  public Shoot(DoubleSupplier manualIndexerSpeed, Shooter shooter, Gateway gateway, Indexer indexer, Limelight limelight, DoubleSupplier rpmTarget) {
    addCommands(
      // Spin shooter wheel
      new SpinShooter(shooter, rpmTarget),
      new RunCommand(() -> {
        // At speed, spin gateway and indexer
        double manualSpeed = manualIndexerSpeed.getAsDouble();
        if(Math.abs(shooter.getClosedLoopErrorRPM()) < kAcceptableShooterRPMError) {
          gateway.spin(.5);
          indexer.spin(Math.abs(manualSpeed) > 0 ? manualSpeed : .5);
        } else {
          gateway.spin(0);
          indexer.spin(manualSpeed);
        }
      }, gateway, indexer)
    );
  }

  public Shoot(DoubleSupplier manualIndexerSpeed, Shooter shooter, Gateway gateway, Indexer indexer, Limelight limelight) {
    this(manualIndexerSpeed, shooter, gateway, indexer, limelight, () -> shooter.getXuru((limelight.getDistance())));
  }
}
