package dbl.findpro.core.domain.model.userGroupsAndProfiles

data class AdminProfile(
    override val userId: String,
    override val profileId: String,
    override val profileType: ProfileType =ProfileType.ADMIN,
    ) : UserProfile
