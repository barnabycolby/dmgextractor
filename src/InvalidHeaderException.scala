package DMGExtractor

object InvalidHeaderExceptionType extends Enumeration {
	type InvalidHeaderExceptionType = Value
	val Missing, WrongVersion = Value
}
import InvalidHeaderExceptionType._

class InvalidHeaderException(exceptionType: InvalidHeaderExceptionType) extends Exception {
	override def getMessage: String = exceptionType match {
		case Missing 			=> "KOLY header was missing"
		case WrongVersion => "The header is of the wrong version"
	}

	/**
	 * Gets the type of the exception
	 *
	 * Get the type of the exception at a more detailed level than just that it was invalid
	 * @return The exceptions type
	 */
	def getType: InvalidHeaderExceptionType = exceptionType
}
