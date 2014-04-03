package DMGExtractor

import org.scalatest._

class DMGSpec extends UnitSpec {
	
	"A DMG" should "throw IllegalArgumentException if no path is given" in {
		a [IllegalArgumentException] should be thrownBy {
			new DMG(null)
		}
	}

	it should "throw InvalidDMGFileException of type EmptyFilePath if an empty path is given" in {
		val exception = intercept[InvalidDMGFileException] {
			new DMG("")
		}
		assert(exception.getType == InvalidDMGFileExceptionType.EmptyFilePath)
	}

	it should "throw InvalidDMGFileException of type FileNotFound if an invalid path is given" in {
		val exception = intercept[InvalidDMGFileException] {
			new DMG("/%&£fdjkasl;$")
		}
		assert(exception.getType == InvalidDMGFileExceptionType.FileNotFound)
	}

	it should "throw InvalidDMGFileException of type Directory if the path of a directory is given" in {
		val exception = intercept[InvalidDMGFileException] {
			new DMG("testFiles/aDirectory")
		}
		assert(exception.getType == InvalidDMGFileExceptionType.Directory)
	}

	it should "throw InvalidDMGFileException of type TooShort if the file length is less than the length of the header (512 bytes)" in {
		val exception = intercept[InvalidDMGFileException] {
			new DMG("testFiles/notLongEnough.dmg")
		}
		assert(exception.getType == InvalidDMGFileExceptionType.TooShort)
	}
}
