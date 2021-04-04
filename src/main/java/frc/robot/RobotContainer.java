/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.SpinIndexer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AlignToTarget;
import frc.robot.commands.ManualAlign;
import frc.robot.commands.PravshotDrive;
import frc.robot.commands.Shoot;
import frc.robot.commands.SpinGateway;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  XboxController m_driver = new XboxController(0);
  XboxController m_operator = new XboxController(1);

  // The robot's subsystems and commands are defined here...
  Drivetrain m_drivetrain = new Drivetrain();
  Limelight m_limelight = new Limelight();
  Compressor m_compressor = new Compressor(Constants.kCompressorPort);
  Indexer m_indexer = new Indexer();
  Gateway m_gateway = new Gateway();
  Shooter m_shooter = new Shooter();

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {  
    // Setup smartdashboard for testing
    setupSmartDashboard();

    // Configure the button bindings
    configureButtonBindings();
  }

  private void setupSmartDashboard() {
    SmartDashboard.putNumber("Test Shooting RPM", 2000);
    SmartDashboard.putData("Test Shooting Routine", new Shoot(m_shooter, m_gateway, m_limelight, 
      () -> SmartDashboard.getNumber("Test Shooting RPM", 0))
    );

    SmartDashboard.putData("Raise shooter", new InstantCommand(() -> m_shooter.raise()));
    SmartDashboard.putData("Lower shooter", new InstantCommand(() -> m_shooter.lower()));
  }

  private void configureButtonBindings() {
    /**
     * Driver
     */

    // (R STICK Y, L STICK X): Forward, turn
    // Pravshot drive ;}
    m_drivetrain.setDefaultCommand(
      new PravshotDrive(
        () -> -m_driver.getRawAxis(XboxController.Axis.kRightY.value), 
        () -> m_driver.getRawAxis(XboxController.Axis.kLeftX.value),
        m_drivetrain)
    );

    // (R BUMPER): Slow mode
    final double slowFactor = .6;
    new JoystickButton(m_driver, XboxController.Button.kBumperRight.value)
      .whenHeld(new PravshotDrive(
        () -> slowFactor * -m_driver.getRawAxis(XboxController.Axis.kRightY.value), 
        () -> slowFactor * m_driver.getRawAxis(XboxController.Axis.kLeftX.value),
        m_drivetrain)
    );

    // (DPAD UP): Auto align to target
    new Button(() -> m_driver.getPOV() == 0)
      .whenHeld(new AlignToTarget(m_drivetrain, m_limelight)
    );

    // (L BUMPER): Turn only mode for manual target alignment. Vibrates the based off of error
    new JoystickButton(m_driver, XboxController.Button.kBumperLeft.value)
      .whenHeld(new ManualAlign(
        () -> m_driver.getRawAxis(XboxController.Axis.kLeftX.value), 
        m_driver, m_drivetrain, m_limelight)
    );

    /**
     * Operator
     */

    // (X): Shooting routine
    new JoystickButton(m_operator, XboxController.Button.kX.value)
        .whenHeld(new Shoot(m_shooter, m_gateway, m_limelight)
    );

    // (L STICK Y): Gateway control, which spins outwards by default
    m_gateway.setDefaultCommand(
      new SpinGateway(
        m_gateway, () -> m_operator.getRawAxis(XboxController.Axis.kRightX.value), -.15)
    );

    // (R STICK X): Indexer control, which spins by default
    m_indexer.setDefaultCommand(
      new SpinIndexer(
        m_indexer, () -> m_operator.getRawAxis(XboxController.Axis.kRightX.value), .15)
    );

    // (BACK): Toggle shooter piston
    new JoystickButton(m_operator, XboxController.Button.kBack.value)
      .whenPressed(new RunCommand(() -> m_shooter.toggleHeight(), m_shooter)
    );
  }
}
