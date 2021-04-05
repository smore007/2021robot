// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class AutoAlign extends PIDCommand {

  static final double minTurn = .37; // Minimum amount for robot to actually turn
  
  /** Creates a new AlignToTarget. */
  public AutoAlign(DoubleSupplier forward, Drivetrain drivetrain, Limelight limelight) {
    super(
        // The controller that the command will use
        new PIDController(0.008, 0.0008, 0.0006),
        // This should return the measurement
        () -> limelight.getOffsetX(),
        // This should return the setpoint (can also be a constant)
        () -> 0,
        // Use the output here
        output -> {
          output += Math.copySign(minTurn, output);

          drivetrain.arcadeDrive(forward.getAsDouble(), output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);

    getController().setTolerance(0.25, 1);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
