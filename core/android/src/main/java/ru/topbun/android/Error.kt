package ru.topbun.android

import kotlinx.coroutines.CoroutineExceptionHandler


suspend fun wrapperException(tryBlock: suspend () -> Unit, onFinally: () -> Unit = {}, onError: (e: Exception, msg: String) -> Unit){
    try {
        tryBlock()
    } catch (e: FailedExtractTokenException) {
        onError(e, "Вы не авторизованы")
    } catch (e: ClientException) {
        onError(e, e.errorText)
    } catch (e: ServerException){
        onError(e, e.errorText)
    } catch (e: RequestTimeoutException) {
        onError(e, "Время ожидания истекло")
    }  catch (e: ParseBackendResponseException) {
        onError(e, "Не удалось получить данные с сервера")
    } catch (e: ConnectException){
        onError(e, "Проверьте интернет подключение")
    } finally {
        onFinally()
    }
}

fun handlerTokenException(action: () -> Unit) = CoroutineExceptionHandler { _, throwable ->
    if (throwable is FailedExtractTokenException) action()
}

