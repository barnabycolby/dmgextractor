package DMGExtractor

import org.scalatest._
import java.io._
import java.math.BigInteger

class HeaderSpec extends UnitSpec {
	
	"A Header" should "throw InvalidHeaderException, of type Missing, if the file does not contain a KOLY header" in {
		val exception = intercept[InvalidHeaderException] {
				new Header(this.getHeaderBytesFrom("testFiles/header/noKolyHeader.dmg"))
		}
		assert(exception.getType == InvalidHeaderExceptionType.Missing)
	}

	it should "throw InvalidHeaderException, of type TooShort, if the header bytes array is not long enough" in {
		val exception = intercept[InvalidHeaderException] {
			val headerBytes = this.getHeaderBytesFrom("testFiles/genuine.dmg")
			val headerBytesSubset = headerBytes.slice(0, 325)
			new Header(headerBytesSubset)
		}
		assert(exception.getType == InvalidHeaderExceptionType.TooShort)
	}

	it should "throw InvalidHeaderException, of type WrongVersion, if the header is not of version 4" in {
		val exception = intercept[InvalidHeaderException] {
				new Header(this.getHeaderBytesFrom("testFiles/header/wrongVersion.dmg"))
		}
		assert(exception.getType == InvalidHeaderExceptionType.WrongVersion)
	}

	it should "throw InvalidHeaderException, of type WrongLength, if the headers length value is not equal to 512" in {
		val exception = intercept[InvalidHeaderException] {
				new Header(this.getHeaderBytesFrom("testFiles/header/wrongLengthValue.dmg"))
		}
		assert(exception.getType == InvalidHeaderExceptionType.WrongLength)
	}

	it should "parse the data fork offset correctly" in {
		val header = this.instantiateHeaderForFile("testFiles/header/dataForkOffset.dmg")
		val expectedValue = new BigInteger("119")
		assert(header.dataForkOffset == expectedValue)
	}

	it should "parse the data fork length correctly" in {
		val header = this.instantiateHeaderForFile("testFiles/genuine.dmg")
		val expectedValue = new BigInteger("1081276")
		assert(header.dataForkLength == expectedValue)
	}

	it should "parse the resource fork offset correctly" in {
		val header = this.instantiateHeaderForFile("testFiles/header/resourceForkOffset.dmg")
		val expectedValue = new BigInteger("13682")
		assert(header.resourceForkOffset == expectedValue)
	}

	it should "parse the resource fork length correctly" in {
		val header = this.instantiateHeaderForFile("testFiles/header/resourceForkLength.dmg")
		val expectedValue = new BigInteger("3342336")
		assert(header.resourceForkLength == expectedValue)
	}

	it should "parse the segment number correctly" in {
		val header = this.instantiateHeaderForFile("testFiles/genuine.dmg")
		val expectedValue = 1
		assert(header.segmentNumber == expectedValue)
	}

	/**
	 * Instantiates the header object for the specified file
	 * @param The filepath of the file to instantiate the header object for
	 * @return The instantiated header object
	 */
	private def instantiateHeaderForFile(filePath: String): Header = {
		new Header(this.getHeaderBytesFrom(filePath))
	}

	/**
	 * Gets the header bytes from the file referenced by the file path
	 *
	 * Gets the last 512 bytes from the file referenced by the file path, these bytes should contain the DMG file header
   *
	 * @param filePath The path of the file to get the header bytes from
	 * @return A byte array containing the bytes of the header
	 */
	private def getHeaderBytesFrom(filePath: String): Array[Byte] = {
		val file = new File(filePath)
		val randomAccessFile = new RandomAccessFile(file, "r")
		return this.getHeaderBytesFrom(randomAccessFile)
	}

	/**
	 * Gets the header bytes from the file referenced by the file object
	 *
	 * Gets the last 512 bytes from the file referenced by the file object, these bytes should contain the DMG file header
	 *
	 * @param file The file object to get the header bytes from
	 * @return A byte array containing the bytes of the header
	 */
	private def getHeaderBytesFrom(file: RandomAccessFile): Array[Byte] = {
		// Work out the position that we should access the header from
		val sizeOfHeader = 512
		val seekPosition = file.length - sizeOfHeader
		
		// Read the header bytes
		var headerBytes = new Array[Byte](sizeOfHeader)
		file.seek(seekPosition)
		file.read(headerBytes, 0, 512)

		return headerBytes
	}
}
