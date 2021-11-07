package zaloznaya.olga.app.gifviewer.utils

class BaseResult<T : Any>(
    val status: Status,
    val message: String? = null
) {

    lateinit var data: T

    companion object {

        fun <T : Any> success(data: T) = BaseResult<T>(Status.SUCCESS).apply {
            this.data = data
        }

        fun <T : Any> error(message: String? = null) = BaseResult<T>(Status.ERROR, message)

    }

    enum class Status {
        SUCCESS, ERROR
    }
}