package DMGExtractor

// Used to convert version byte array to an Int
import java.nio.ByteBuffer

// Used to represent 64 bit unsigned integers
import java.math.BigInteger

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

	// Check that the length value is the expected value, 512
	val expectedLengthValue = 512
	val actualLengthValueBytes = headerBytes.slice(8, 12)
	val actualLengthValue = ByteBuffer.wrap(actualLengthValueBytes).getInt
	if (actualLengthValue != expectedLengthValue) {
		throw new InvalidHeaderException(InvalidHeaderExceptionType.WrongLength)
	}

	// Get the data fork offset value from the file
	val dataForkOffsetBytes = headerBytes.slice(24, 32)
	this.dataForkOffset = new BigInteger(dataForkOffsetBytes)

	private var _dataForkOffset: BigInteger = _

	/**
	 * Gets the data fork offset value
	 *
	 * Gets the data fork offset value, parsed from the file
	 * @return The data fork offset value
	 */
	def dataForkOffset = this._dataForkOffset

	/**
	 * Sets the data fork offset value, parsed from the file
	 * @param dataForkOffset The dataForkOffset value
	 */
	private def dataForkOffset_=(dataForkOffset: BigInteger) {
		this._dataForkOffset = dataForkOffset
	}
}
