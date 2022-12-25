import arc.graphics.Color

enum class AnsiColor(val color: Int, val hex : String, val background: Boolean = false) {
    gray(30, "51545c"), red(31, "ba372d"), green(32, "8b9814"),
    yellow(33, "a8880e"), blue(34, "5b8cd1"), pink(35, "ab3c81"),
    cyan(36, "66a098"), white(37, "eaffff"), darkBlueBG(40, "162b36", true),
    orangeBG(41, "ae4d16", true), blueBG(42, "606e75", true),
    greyishBG(43, "6d7b83", true), grayBG(44, "899496", true),
    indigoBG(45, "6f73c3", true), lightGrayBG(46, "98a1a1", true),
    whiteBG(47, "fbf6e3", true), clearBG(0, "00000000", true);

    companion object {
        fun closest(color: Color): AnsiColor {
            if (color.a == 0f) return clearBG
            return values().sortedBy{it.distance(color)}[0]
        }
    }

    override fun toString(): String {
        return "\u001b[0;${color}m"
    }

    fun distance(color: Color): Float {
        return Color.valueOf(hex).diff(color)
    }
}