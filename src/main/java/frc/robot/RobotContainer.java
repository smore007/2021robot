/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.SpinIndexer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.commands.SpinGateway;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  XboxController m_controller = new XboxController(0);

  // The robot's subsystems and commands are defined here...
  Drivetrain m_drivetrain = new Drivetrain();
  //Indexer m_indexer = new Indexer();
  //Gateway m_gateway = new Gateway();
  //Shooter m_shooter = new Shooter();

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Passively spin indexer, gateway
    //m_indexer.setDefaultCommand(new SpinIndexer(m_indexer, () -> .1));
    //m_gateway.setDefaultCommand(new SpinGateway(m_gateway, () -> -.1));

    // Configure the button bindings
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    // Pravshot drive
    m_drivetrain.setDefaultCommand(
      new RunCommand(() -> {
          double forward = -m_controller.getRawAxis(XboxController.Axis.kRightY.value);
          double turn = m_controller.getRawAxis(XboxController.Axis.kLeftX.value);
          m_drivetrain.arcadeDrive(
            Math.copySign(Math.pow(Math.sin(Math.PI / 2 * forward), 2), forward), 
            Math.copySign(Math.pow(Math.sin(Math.PI / 2 * turn), 2), turn)
          );
        }, 
        m_drivetrain
      )
    );
  }
}
