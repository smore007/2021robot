// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Gateway;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class Shoot extends SequentialCommandGroup {
  /** Creates a new WarmUpShooter. */
  public Shoot(Shooter shooter, Gateway gateway, Drivetrain drivetrain, Limelight limelight, DoubleSupplier rpmTarget) {
    addCommands(
      // Put shooter in correct position
      new InstantCommand(
        () -> {
          if(shooter.shouldBeRaised(limelight.getDistance()))
            shooter.raise();
          else
            shooter.lower();
        }
      ),
      // Line up to target
      new AlignToTarget(drivetrain, limelight),
      // Rev up to rpm, move on when its hit
      new RunCommand(() -> shooter.setRPM(rpmTarget.getAsDouble()), shooter)
        .withInterrupt(() -> Math.abs(shooter.getClosedLoopErrorRPM()) < Constants.kAcceptableShooterError),
      // Finally shoot and spin gateway
      new SpinShooter(shooter, () -> rpmTarget.getAsDouble())
        .alongWith(new SpinGateway(gateway, () -> 1))
    );
  }

  public Shoot(Shooter shooter, Gateway gateway, Drivetrain drivetrain, Limelight limelight) {
    this(shooter, gateway, drivetrain, limelight, () -> Constants.xuru(limelight.getDistance(), shooter.isRaised()));
  }
}
