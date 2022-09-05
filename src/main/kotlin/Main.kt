import arc.files.Fi
import arc.graphics.Color
import arc.graphics.PixmapIO
import arc.util.Timer
import arc.util.Timer.Task
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import kotlin.math.max

fun main(args: Array<String>) {
    if (args.size != 3) {
        println("use this with these args:")
        println("<input png> <output text> <character width>")
        return
    }
    val path = args[0]
    val out = args[1]
    val size = args[2].toInt()

    val input = PixmapIO.readPNG(Fi(path))
    val output = Fi(out)

    val div = input.width / input.height.toDouble()
    val xAdd = max(1.0, input.getWidth() / size.toDouble())
    val yAdd = max(1.0, input.getHeight() / size.toDouble()) * div
    var str = ""
    var last: Int? = null
    val color = Color()
    for (y in 0 until (size / div).toInt()) {
        for (x in 0 until size) {
            color.set(input.get(((x + 0.5) * xAdd).toInt(), ((y + 0.5) * yAdd).toInt()))
            if (last != color.rgba8888()) str += "[#$color]"
            str += "\uF8ED" // salt emoji
            last = color.rgba8888()
        }
        str += "\\n"
    }
    if (str.isNotEmpty()) str = str.substring(0, str.length - 2)
    println("${str.length} length (${if (str.length > 15000) "will prob crash" else "should work"})")
    output.writeString(str)
    output.writeString("\nfor discord:\n", true)
    output.writeString(str.replace("(.{1800})".toRegex(), "$1\n"), true)
}