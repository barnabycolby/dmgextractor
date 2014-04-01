package DMGExtractor

class InvalidDMGFileException(message: String) extends Exception {
	override def getMessage = super.getMessage + message
}
