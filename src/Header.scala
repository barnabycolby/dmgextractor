package DMGExtractor

import java.io.RandomAccessFile

class Header(val file: RandomAccessFile) {
	// Work out what the position that we should access the header from
	val seekOffset = file.length - 512

	// Read the header bytes
	var header = new Array[Byte](512)
	file.seek(seekOffset)
	file.read(header, 0, 512)

	// The first 4 bytes should contain the magic KOLY value
	// Construct the expected and actual magic
	var expectedMagic: Array[Byte] = Array(107, 111, 108, 121)
	var actualMagic = header.slice(0, 4)

	// Check whether they're equal
	if (!actualMagic.sameElements(expectedMagic)) {
		throw new InvalidHeaderException(InvalidHeaderExceptionType.Missing)
	}
}
