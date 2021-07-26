package zaloznaya.olga.app.gifviewer.data.remote.retrofit

import retrofit2.Retrofit

inline fun <reified T> createWebService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}