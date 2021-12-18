package id.co.ptn.hungrystock.models.registration
class MainRegistration(
 var id: String,
 var title: String,
 var items: MutableList<RegistrationItem>,
 var value:String
) {
 companion object {
  const val DOMISILI = "domisili"
  const val LONGINVEST = "longinvest"
  const val PROFESI = "profesi"
  const val PENDIDIKAN = "pendidikan"
  const val PORTOFOLIO = "portofolio"
 }
}

class RegistrationItem(
 var title: String,
 var selected: Boolean
)