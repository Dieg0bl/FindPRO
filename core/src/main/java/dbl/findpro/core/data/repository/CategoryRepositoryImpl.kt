package dbl.findpro.core.data.repository

import dbl.findpro.core.domain.model.userGroupsAndProfiles.Category
import dbl.findpro.core.domain.repository.ICategoryRepository
import dbl.findpro.core.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor() : ICategoryRepository {

    private val categories = listOf(
        Category("albanil", "Albañil", R.drawable.ic_albanil),
        Category("autolavador", "Autolavador", R.drawable.ic_autolavador),
        Category("carpintero", "Carpintero", R.drawable.ic_carpintero),
        Category("cuidador", "Cuidador", R.drawable.ic_cuidador),
        Category("decorador", "Decorador", R.drawable.ic_decorador),
        Category("diseno_grafico", "Diseñador Gráfico", R.drawable.ic_diseno_grafico),
        Category("electricista", "Electricista", R.drawable.ic_electricista),
        Category("fontanero", "Fontanero", R.drawable.ic_fontanero),
        Category("fotografo", "Fotógrafo", R.drawable.ic_fotografo),
        Category("jardinero", "Jardinero", R.drawable.ic_jardinero),
        Category("limpiador", "Limpiador", R.drawable.ic_limpiador),
        Category("mecanico", "Mecánico", R.drawable.ic_mecanico),
        Category("motosierra_forestal", "Motosierrista Forestal", R.drawable.ic_motosierra_forestal),
        Category("palista", "Palista", R.drawable.ic_palista),
        Category("programador", "Programador", R.drawable.ic_programador),
        Category("repartidor", "Repartidor", R.drawable.ic_repartidor),
        Category("soldador", "Soldador", R.drawable.ic_soldador),
        Category("tecnico_redes", "Técnico de Redes", R.drawable.ic_tecnico_redes),
        Category("transportista", "Transportista", R.drawable.ic_transportista)
    )

    override fun getAllCategories(): List<Category> = categories

    override fun getCategoryById(categoryId: String): Category? {//Return type of 'getCategoryById' is not a subtype of the return type of the overridden member 'fun getCategoryById(string: String): Unit' defined in 'dbl/ findpro/ core/ domain/ repository/ ICategoryRepository'.
        return categories.find { it.categoryId == categoryId }
    }
}
