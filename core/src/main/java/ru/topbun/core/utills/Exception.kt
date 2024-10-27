package ru.topbun.tasty.utills

open class AppException: RuntimeException()

class ParseBackendResponseException(val errorText: String = "") : AppException()
class RequestTimeoutException(val errorText: String = "") : AppException()
class ClientException(val errorText: String = "") : AppException()
class ServerException(val errorText: String = "") : AppException()
class ConnectException(val errorText: String = ""): AppException()

class FailedExtractTokenException : AppException()
class AccountInfoNotCompleteException(val errorText: String = "") : AppException()
