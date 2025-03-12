package dbl.findpro.core.domain.repository

import dbl.findpro.core.domain.model.userGroupsAndProfiles.Category

interface ICategoryRepository {
    fun getAllCategories(): List<Category>
    fun getCategoryById(categoryId: String): Category?
}
