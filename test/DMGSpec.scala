package DMGExtractor

import org.scalatest._

class DMGSpec extends UnitSpec {
	
	"A DMG" should "throw IllegalArgumentException if no filename is given" in {
		a [IllegalArgumentException] should be thrownBy {
			new DMG(null)
		}
	}

	it should "throw InvalidDMGFileException if an empty filename is given" in {
		a [InvalidDMGFileException] should be thrownBy {
			new DMG("")
		}
	}

	it should "throw InvaildDMGFileException if an invalid filename is given" in {
		a [InvalidDMGFileException] should be thrownBy {
			new DMG("/%&£fdjkasl;$")
		}
	}
}
