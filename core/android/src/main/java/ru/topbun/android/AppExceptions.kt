package ru.topbun.android

open class AppException(val errorText: String = ""): RuntimeException()

class ParseBackendResponseException : AppException()
class RequestTimeoutException : AppException()
class ClientException(errorText: String = "") : AppException(errorText)
class ServerException(errorText: String = "") : AppException(errorText)
class ConnectException: AppException()
class FailedExtractTokenException : AppException()
