package br.com.frizeiro.admobkit.support

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences.Editor

/**
 * Created by Felipe Frizeiro on 02/02/23.
 */
internal class PreferencesManager(context: Context) {

    // region Private Variables

    private val prefs = context.getSharedPreferences("ADMOB_KIT_PREFS", MODE_PRIVATE)

    // endregion

    // region Public Methods

    fun save(key: String, value: Long) = save { it.putLong(key, value) }

    fun getLong(key: String) = prefs.getLong(key, -1L).let {
        if (it == -1L) null else it
    }

    // endregion

    // region Private Methods

    private fun save(saveHandler: (Editor) -> Unit) {
        val editor = prefs.edit()
        saveHandler(editor)
        editor.apply()
    }

    // endregion
}