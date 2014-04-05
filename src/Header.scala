package DMGExtractor

// Used to convert version byte array to an Int
import java.nio.ByteBuffer

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
	val expectedMagic: Array[Byte] = Array(107, 111, 108, 121)
	val actualMagic = headerBytes.slice(0, 4)

	// Check whether they're equal
	if (!actualMagic.sameElements(expectedMagic)) {
		throw new InvalidHeaderException(InvalidHeaderExceptionType.Missing)
	}

	// Check that the version is the expected value, 4
	val expectedVersion = 4
	val actualVersionBytes = headerBytes.slice(4, 8)
	val actualVersion = ByteBuffer.wrap(actualVersionBytes).getInt
	if (actualVersion != expectedVersion) {
		throw new InvalidHeaderException(InvalidHeaderExceptionType.WrongVersion)
	}
}
