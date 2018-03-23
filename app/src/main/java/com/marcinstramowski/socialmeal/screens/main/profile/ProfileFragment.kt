package com.marcinstramowski.socialmeal.screens.main.profile

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.ArrayAdapter
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.marcinstramowski.socialmeal.GlideApp
import com.marcinstramowski.socialmeal.R
import com.marcinstramowski.socialmeal.extensions.format
import com.marcinstramowski.socialmeal.model.profile.FoodTypes
import com.marcinstramowski.socialmeal.model.profile.ProfileUpdateRequest
import com.marcinstramowski.socialmeal.screens.account.AccountActivity
import com.marcinstramowski.socialmeal.screens.base.BaseFragment
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_profile.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * Screen that allows user view and manage his profile
 */
class ProfileFragment : BaseFragment<ProfileContract.Presenter>(), ProfileContract.View {

    @Inject
    override lateinit var presenter: ProfileContract.Presenter
    override val contentViewId = R.layout.fragment_profile

    override fun onCreated(savedInstanceState: Bundle?) {
        configureFoodTypeSpinner()
        profileSignOut.setOnClickListener { presenter.onSignOutButtonPressed() }
        profileChangePassword.setOnClickListener { presenter.onChangePasswordPressed() }
        saveProfile.setOnClickListener { presenter.onSaveProfileButtonPressed(getProfileRequest()) }

    }

    private fun configureFoodTypeSpinner() {
        val adapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_dropdown_item,
            FoodTypes.getNames(context!!)
        )
        profileFavouriteFood.adapter = adapter
    }

    private fun getProfileRequest(): ProfileUpdateRequest {
        return ProfileUpdateRequest(
            profileName.text.toString(),
            profileSurname.text.toString(),
            profileAbout.text.toString(),
            listOf(FoodTypes.values()[profileFavouriteFood.selectedItemPosition].toString())
        )
    }

    override fun setUserAvatar(avatarUrl: String) {
        val image = ContextCompat.getDrawable(context!!, R.drawable.hugh)
        GlideApp.with(this)
            .load(image)
            .apply(bitmapTransform(BlurTransformation(25)))
            .into(profileBackground)
        GlideApp.with(this)
            .load(image)
            .into(profileAvatar)
    }

    override fun showUserName(name: String) {
        profileName.setText(name)
    }

    override fun showUserSurname(surname: String) {
        profileSurname.setText(surname)
    }

    override fun showUserRating(rating: Double) {
        userScore.text = rating.format(2)
    }

    override fun showErrorMessage(messageId: Int) {
        toast(messageId)
    }

    override fun signOut() {
        activity?.finish()
        startActivity<AccountActivity>()
    }

    override fun enableSaveButton(enabled: Boolean) {
        saveProfile.isEnabled = enabled
    }

    override fun showProfileUpdateSuccessMessage() {
        toast(R.string.profile_update_success)
    }

    override fun showUserDescription(description: String) {
        profileAbout.setText(description)
    }

    override fun showFavouriteFood(favouriteFoodType: FoodTypes) {
        profileFavouriteFood.setSelection(favouriteFoodType.ordinal)
    }

    override fun showNotYetImplementedMessage() {
        toast(R.string.not_implemented_yet)
    }
}