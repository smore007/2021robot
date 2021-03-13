/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  
  WPI_TalonFX m_master = new WPI_TalonFX(Constants.kShooterTalonMaster);
  WPI_TalonFX m_slave = new WPI_TalonFX(Constants.kShooterTalonSlave);
  DoubleSolenoid m_piston = new DoubleSolenoid(Constants.kShooterPistonForward, Constants.kShooterPistonReverse);

  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    m_master.setSensorPhase(true);
    
    m_slave.follow(m_master);
    m_slave.setInverted(InvertType.OpposeMaster);
  }


  /**
   * Raising / lowering with piston
   */
  public void raise() {
    m_piston.set(Value.kForward);
  }

  public void lower() {
    m_piston.set(Value.kReverse);
  }

  public boolean isRaised() {
    return m_piston.get() == Value.kForward;
  }

  public boolean shouldBeRaised(double distance) {
    return distance > Constants.kDistanceToRaise;
  }

  /**
   * Shooting
   */
  public double getRPM() {
    return getRPMFromVelocity(m_master.getSelectedSensorVelocity());
  }

  public void setRPM(double rpm) {
    m_master.set(ControlMode.Velocity, getVelocityFromRPM(rpm));
  }

  public double getClosedLoopErrorRPM() {
    return getRPMFromVelocity(m_master.getClosedLoopError());
  }

  // Converts RPM to talon units
  double getVelocityFromRPM(double rpm) {
    return (rpm / 600) * 4096;
  }

  // Converts talon units to RPM
  double getRPMFromVelocity(double velocity) {
    return (velocity * 600) / 4096;
  }
}
