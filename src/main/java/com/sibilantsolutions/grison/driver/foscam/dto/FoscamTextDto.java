package com.sibilantsolutions.grison.driver.foscam.dto;

/**
 * A Text Dto represents the "text" or payload of each Foscam Command packet.
 *
 * A Dto (Data Transfer Object) is meant to closely model the bytes on the wire.  These are then translated to
 * Entities that interpret the bytes into a higher-level class or data type.
 *
 * Specifically, Dtos in this package should hold fields representing only the low-level types from package
 * com.sibilantsolutions.grison.driver.foscam.type.
 *
 * This is a base interface implemented by all Dtos and can be treated as a supertype for all of them.
 */
public interface FoscamTextDto {

    FoscamOpCode opCode();

    int encodedLength();

}
