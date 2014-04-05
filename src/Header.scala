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
	// Check that the header is not too short
	if (headerBytes.length != 512) {
		throw new InvalidHeaderException(InvalidHeaderExceptionType.TooShort)
	}

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
	val actualVersion = get32BitIntStartingAt(4)
	if (actualVersion != expectedVersion) {
		throw new InvalidHeaderException(InvalidHeaderExceptionType.WrongVersion)
	}

	// Check that the length value is the expected value, 512
	val expectedLengthValue = 512
	val actualLengthValue = get32BitIntStartingAt(8)
	if (actualLengthValue != expectedLengthValue) {
		throw new InvalidHeaderException(InvalidHeaderExceptionType.WrongLength)
	}

	// Get the data fork offset value from the file
	this.dataForkOffset = getIntegerByByteRange(24, 32)

	// Get the data fork length value from the file
	this.dataForkLength = getIntegerByByteRange(32, 40)

	// Get the resource fork offset value from the file
	this.resourceForkOffset = getIntegerByByteRange(40, 48)

	/**
	 * Gets a 32 bit integer from the header that starts at the specified array index
	 * @param start The index in the header bytes array that the integer starts at
	 * @return The 32 bit integer
	 */
	private def get32BitIntStartingAt(start: Int): Int = {
		val integerBytes = headerBytes.slice(start, start + 4)
		return ByteBuffer.wrap(integerBytes).getInt
	}

	/**
	 * Gets an integer from the header that is represented by the range specified
	 * @param from The start of the range, inclusive
	 * @param until The end of the range, exclusive
	 * @return A BigInteger created from the sub array
	 */
	private def getIntegerByByteRange(from: Int, until: Int): BigInteger = {
		val integerBytes = headerBytes.slice(from, until)
		new BigInteger(integerBytes)
	}

	private var _dataForkOffset: BigInteger = _
	private var _dataForkLength: BigInteger = _
	private var _resourceForkOffset: BigInteger = _

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

	/**
	 * Gets the data fork length value
	 *
	 * Gets the data fork length value, parsed from the file
	 * @return The data fork length value
	 */
	def dataForkLength = this._dataForkLength

	/**
	 * Sets the data fork length value, parsed from the file
	 * @param dataForkLength The dataForkLength value
	 */
	private def dataForkLength_=(dataForkLength: BigInteger) {
		this._dataForkLength = dataForkLength
	}

	/**
	 * Gets the resource fork offset value, parsed from the file
	 * @return The resource fork offset value
	 */
	def resourceForkOffset = this._resourceForkOffset

	/**
	 * Sets the resource fork offset value, parsed from the file
	 * @param resourceForkOffset The resource fork offset value
	 */
	private def resourceForkOffset_=(resourceForkOffset: BigInteger) {
		this._resourceForkOffset = resourceForkOffset
	}
}
