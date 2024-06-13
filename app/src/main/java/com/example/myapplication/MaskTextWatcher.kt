import android.text.Editable
import android.text.TextWatcher

class MaskTextWatcher(private val mask: String) : TextWatcher {

    private var isUpdating: Boolean = false
    private var oldString: String = ""

    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
        val str = unmask(charSequence.toString())
        var maskedString = ""

        if (isUpdating) {
            oldString = str
            isUpdating = false
            return
        }

        var index = 0
        for (char in mask.toCharArray()) {
            if (char != '#' && str.length > oldString.length) {
                maskedString += char
                continue
            }

            try {
                maskedString += str[index]
            } catch (e: Exception) {
                break
            }

            index++
        }

        isUpdating = true
        (charSequence as Editable).replace(0, charSequence.length, maskedString)
    }

    override fun afterTextChanged(editable: Editable) {}

    private fun unmask(s: String): String {
        return s.replace("[^\\d]".toRegex(), "")
    }
}