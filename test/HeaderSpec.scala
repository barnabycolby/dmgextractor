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

	def getHeaderBytesFrom(filePath: String): Array[Byte] = {
		val file = new File(filePath)
		val randomAccessFile = new RandomAccessFile(file, "r")
		return this.getHeaderBytesFrom(randomAccessFile)
	}

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
