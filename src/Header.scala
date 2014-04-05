package DMGExtractor

import java.io.RandomAccessFile

/**
 * Parses the header of a DMG file and stores it's data
 *
 * @param headerBytes The header bytes contained in a byte array, should be 512 bytes long
 * @throws InvalidHeaderException If the header is invalid or missing
 *
 * @author Barnaby Colby
 */
class Header(val headerBytes: Array[Byte]) {
	// The first 4 bytes should contain the magic KOLY value
	// Construct the expected and actual magic
	var expectedMagic: Array[Byte] = Array(107, 111, 108, 121)
	var actualMagic = headerBytes.slice(0, 4)

	// Check whether they're equal
	if (!actualMagic.sameElements(expectedMagic)) {
		println("["+actualMagic(0)+","+actualMagic(1)+","+actualMagic(2)+","+actualMagic(3)+"]")
		throw new InvalidHeaderException(InvalidHeaderExceptionType.Missing)
	}
}
