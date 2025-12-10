// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

// ===== Input Devices ===== //
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

// ===== Swerve Specific ===== //
import frc.robot.commands.SwerveJoystickCmd;
import frc.robot.subsystems.SwerveSubsystem;

// ===== Auxiliary Motor ===== //
import frc.robot.subsystems.AuxiliaryMotorSubsystem;
import frc.robot.commands.RunAuxiliaryMotorCmd;

// ===== Constants ===== //
import frc.robot.Constants.OIConstants;

public class RobotContainer {

    // Subsystems
    private final SwerveSubsystem swerveSubsystem;
    private final AuxiliaryMotorSubsystem auxiliaryMotorSubsystem;

    // Control Inputs
    private final Joystick controller = new Joystick(OIConstants.kOperatorControllerPort);

    public RobotContainer() {

        swerveSubsystem = new SwerveSubsystem();
        auxiliaryMotorSubsystem = new AuxiliaryMotorSubsystem();

        // Swerve drive default command
        swerveSubsystem.setDefaultCommand(new SwerveJoystickCmd(
            swerveSubsystem,
            () -> -controller.getRawAxis(OIConstants.kDriverYAxis),
            () -> -controller.getRawAxis(OIConstants.kDriverXAxis),
            () -> -controller.getRawAxis(OIConstants.kDriverRotAxis),
            () -> controller.getRawButton(OIConstants.kController_rightBumper),
            () -> controller.getRawButton(OIConstants.kController_leftBumper)));

        // Auxiliary motor default command (controlled by right stick Y-axis)
        auxiliaryMotorSubsystem.setDefaultCommand(
            new RunAuxiliaryMotorCmd(
                auxiliaryMotorSubsystem,
                () -> -controller.getRawAxis(OIConstants.kAuxiliaryMotorAxis)
            )
        );

        configureBindings();
    }

    private void configureBindings() {
        // Reset gyro
        new JoystickButton(controller, OIConstants.kDriverResetGyroButtonId)
            .onTrue(swerveSubsystem.zeroHeading());
        
        // Example: Run auxiliary motor at full speed when X button is pressed
        new JoystickButton(controller, OIConstants.kController_x)
            .whileTrue(auxiliaryMotorSubsystem.runAtSpeed(0.5));
        
        // Example: Run auxiliary motor in reverse when B button is pressed
        new JoystickButton(controller, OIConstants.kController_b)
            .whileTrue(auxiliaryMotorSubsystem.runAtSpeed(-0.5));
        
        // Example: Stop auxiliary motor when A button is pressed
        new JoystickButton(controller, OIConstants.kController_a)
            .onTrue(auxiliaryMotorSubsystem.stopMotor());
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}