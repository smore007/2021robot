// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Gateway;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class Shoot extends SequentialCommandGroup {
  /** Creates a new WarmUpShooter. */
  public Shoot(Shooter shooter, Gateway gateway, Limelight limelight, DoubleSupplier rpmTarget) {
    addCommands(
      // Rev up to rm
      new SpinShooter(shooter, rpmTarget)
        .withInterrupt(() -> Math.abs(shooter.getClosedLoopErrorRPM()) < Constants.kAcceptableShooterRPMError),
      // When it hits target, open gateway
      new SpinShooter(shooter, rpmTarget)
        .alongWith(new SpinGateway(gateway, () -> 1))
    );
  }

  public Shoot(Shooter shooter, Gateway gateway, Limelight limelight) {
    this(shooter, gateway, limelight, () -> shooter.getXuru(limelight.getDistance()));
  }
}
