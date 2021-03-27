/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  
  WPI_TalonSRX m_leftBack = new WPI_TalonSRX(Constants.kDTBackLeftTalon);
  WPI_TalonSRX m_leftFront = new WPI_TalonSRX(Constants.kDTFrontLeftTalon);
  SpeedControllerGroup m_left = new SpeedControllerGroup(m_leftBack, m_leftFront);

  WPI_TalonSRX m_rightBack = new WPI_TalonSRX(Constants.kDTBackRightTalon);
  WPI_TalonSRX m_rightFront = new WPI_TalonSRX(Constants.kDTFrontRightTalon);
  SpeedControllerGroup m_right = new SpeedControllerGroup(m_rightBack, m_rightFront);

  DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

  /**
   * Creates a new Drivetrain.
   */
  public Drivetrain() {
    m_right.setInverted(true);
    m_left.setInverted(true);
  }

  public void arcadeDrive(double speed, double turn) {
    m_drive.arcadeDrive(speed, turn);
  }

  public void curvatureDrive(double speed, double turn, boolean isQuickTurn) {
    m_drive.curvatureDrive(speed, turn, isQuickTurn);
  }
}
