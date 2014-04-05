package DMGExtractor

import java.io.RandomAccessFile

class Header(val headerBytes: Array[Byte]) {
	// The first 4 bytes should contain the magic KOLY value
	// Construct the expected and actual magic
	var expectedMagic: Array[Byte] = Array(107, 111, 108, 121)
	var actualMagic = headerBytes.slice(0, 4)

	// Check whether they're equal
	if (!actualMagic.sameElements(expectedMagic)) {
		throw new InvalidHeaderException(InvalidHeaderExceptionType.Missing)
	}
}
