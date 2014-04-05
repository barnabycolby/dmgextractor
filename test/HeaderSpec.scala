package DMGExtractor

import org.scalatest._
import java.io._

class HeaderSpec extends UnitSpec {
	
	"A Header" should "throw InvalidHeaderException if the file does not contain a KOLY header" in {
		val exception = intercept[InvalidHeaderException] {
				val file: File = new File("testFiles/noKolyHeader.dmg")
				val randomAccessFile: RandomAccessFile = new RandomAccessFile(file, "r")
				new Header(this.getHeaderBytesFrom(randomAccessFile))
		}
		assert(exception.getType == InvalidHeaderExceptionType.Missing)
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
