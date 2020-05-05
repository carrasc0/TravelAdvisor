package com.gabriel.traveladvisor.network


data class Output<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T): Output<T?>? =
            Output(
                status = Status.SUCCESS,
                data = data,
                message = null
            )

        fun <T> error(data: T?, message: String): Output<T?>? =
            Output(
                status = Status.ERROR,
                data = data,
                message = message
            )

        fun <T> loading(data: T?): Output<T?>? =
            Output(
                status = Status.LOADING,
                data = data,
                message = null
            )
    }
}