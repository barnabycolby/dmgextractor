package DMGExtractor

import org.scalatest._
import java.io._

class HeaderSpec extends UnitSpec {
	
	"A Header" should "throw InvalidHeaderException if the file does not contain a KOLY header" in {
		val exception = intercept[InvalidHeaderException] {
				val file: File = new File("testFiles/noKolyHeader.dmg")
				val randomAccessFile: RandomAccessFile = new RandomAccessFile(file, "r")
				new Header(randomAccessFile)
		}
		assert(exception.getType == InvalidHeaderExceptionType.Missing)
	}
}
