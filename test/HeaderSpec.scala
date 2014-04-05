package DMGExtractor

import org.scalatest._
import java.io._

class HeaderSpec extends UnitSpec {
	
	"A Header" should "throw InvalidHeaderException, of type Missing, if the file does not contain a KOLY header" in {
		val exception = intercept[InvalidHeaderException] {
				new Header(this.getHeaderBytesFrom("testFiles/noKolyHeader.dmg"))
		}
		assert(exception.getType == InvalidHeaderExceptionType.Missing)
	}

	it should "throw InvalidHeaderException, of type WrongVersion, if the header is not of version 4" in {
		val exception = intercept[InvalidHeaderException] {
				new Header(this.getHeaderBytesFrom("testFiles/wrongVersion.dmg"))
		}
		assert(exception.getType == InvalidHeaderExceptionType.WrongVersion)
	}

	/**
	 * Gets the header bytes from the file referenced by the file path
	 *
	 * Gets the last 512 bytes from the file referenced by the file path, these bytes should contain the DMG file header
   *
	 * @param filePath The path of the file to get the header bytes from
	 * @return A byte array containing the bytes of the header
	 */
	def getHeaderBytesFrom(filePath: String): Array[Byte] = {
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
	def getHeaderBytesFrom(file: RandomAccessFile): Array[Byte] = {
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
