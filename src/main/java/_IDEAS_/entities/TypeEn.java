package _IDEAS_.entities;

/**
 * Created by mac on 25.07.2017.
 */
public enum TypeEn {
    DI, // DI, DI14
    DO, // DOS
    AI, // AIK
    AO, // AO, AO6

    // DI based
    Switch,

    // DOS based
    Relay,
    Valve,
    Motor,

    // AI based
    Sensor,

    // AO based
    PWM,

    // Complex types
    Cylinder,
    MotorPWM,
}
