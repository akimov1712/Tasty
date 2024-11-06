package ru.topbun.android

import kotlinx.coroutines.CoroutineExceptionHandler


suspend fun wrapperException(tryBlock: suspend () -> Unit, onFinally: () -> Unit = {}, onError: (String) -> Unit){
    try {
        tryBlock()
    } catch (e: FailedExtractTokenException) {
        onError("Вы не авторизованы")
    } catch (e: ClientException) {
        onError(e.errorText)
    } catch (e: ServerException){
        onError(e.errorText)
    } catch (e: RequestTimeoutException) {
        onError("Время ожидания истекло")
    }  catch (e: ParseBackendResponseException) {
        onError("Не удалось получить данные с сервера")
    } catch (e: ConnectException){
        onError("Проверьте интернет подключение")
    } finally {
        onFinally()
    }
}

fun handlerTokenException(action: () -> Unit) = CoroutineExceptionHandler { _, throwable ->
    if (throwable is FailedExtractTokenException) action()
}

