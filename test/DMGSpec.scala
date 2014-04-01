package DMGExtractor

import org.scalatest._

class DMGSpec extends UnitSpec {
	
	"A DMG" should "throw IllegalArgumentException if no filename is given" in {
		a [IllegalArgumentException] should be thrownBy {
			new DMG(null)
		}
	}

	it should "throw NoSuchFileException if an empty filename is given" in {
		a [InvalidDMGFileException] should be thrownBy {
			new DMG("")
		}
	}
}
