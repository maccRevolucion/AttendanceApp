import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("attendances")
    suspend fun postAsistencia(
        @Field("id_empleado") idEmployee: Int
    ): ApiResponse
}
