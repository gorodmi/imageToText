import arc.files.Fi
import arc.graphics.Color
import arc.graphics.PixmapIO
import kotlin.math.max

fun main(args: Array<String>) {
    if (args.size != 3) {
        println("use this with these args:")
        println("<input png> <output text> <character width>")
        return
    }
    val path = args[0]
    val out = args[1]
    val input = PixmapIO.readPNG(Fi(path))
    val output = Fi(out)
    var size = 50
    var str = ""
    while (size > 0) {
        println("trying size $size")
        val div = input.width / input.height.toDouble() * 2
        val xAdd = input.getWidth() / size.toDouble()
        val yAdd = input.getHeight() / size.toDouble() * div
        str = ""
        var last: AnsiColor? = null
        val color = Color()
        for (y in 0 until (size / div).toInt()) {
            for (x in 0 until size) {
                color.set(input.get((x * xAdd).toInt(), (y * yAdd).toInt()))
                val ansi = AnsiColor.closest(color)
                if (last != ansi) str += ansi
                str += if (ansi.background) " " else "█"
                last = ansi
            }
            str += "\n"
        }
        if (str.isNotEmpty()) str = str.substring(0, str.length - 1)
        str = "```ansi\n$str```"
        if (str.length < 2000) break
        size--
    }
    output.writeString(str)
    println("found size $size")
//    output.writeString("\nfor discord:\n", true)
//    output.writeString(str.replace("(.{1800})".toRegex(), "$1\n"), true)
}