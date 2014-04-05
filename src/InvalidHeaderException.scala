package DMGExtractor

object InvalidHeaderExceptionType extends Enumeration {
	type InvalidHeaderExceptionType = Value
	val Missing, TooShort, WrongVersion, WrongLength = Value
}
import InvalidHeaderExceptionType._

class InvalidHeaderException(exceptionType: InvalidHeaderExceptionType) extends Exception {
	override def getMessage: String = exceptionType match {
		case Missing 			=> "KOLY header was missing"
		case TooShort			=> "The header given was too short"
		case WrongVersion => "The header is of the wrong version"
		case WrongLength  => "The length value in the header is not the expected value"
	}

	/**
	 * Gets the type of the exception
	 *
	 * Get the type of the exception at a more detailed level than just that it was invalid
	 * @return The exceptions type
	 */
	def getType: InvalidHeaderExceptionType = exceptionType
}
