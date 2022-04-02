package id.co.ptn.hungrystock.models.registration

import okhttp3.MultipartBody
import okhttp3.RequestBody

class RequestRegistrationStepOne(
    var photoProfile: MultipartBody.Part,
    var name: RequestBody,
    var noWa: RequestBody,
    var email: RequestBody,
    var birthDate: RequestBody,
    var password: RequestBody,
)