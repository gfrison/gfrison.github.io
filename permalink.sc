//> using dep "com.lihaoyi::os-lib:0.9.3"
val permaregex = "/^permalink: *$/".r
val namergx = "(\\d{4})-(\\d{2})-(\\d+)-(.*).md".r.unanchored
val files =
  os.list(os.pwd / "_posts")
    .filter(_.ext == "md")
    .map(f => (os.read.lines(f), f))
    .map: (lines, f) =>
      if !lines.exists(_.startsWith("permalink: ")) then
        val namergx(year, month, day, pname) = f.toString
        val permalink = s"permalink: /$year/$month/$day/$pname"
        (lines.head +: permalink +: lines.tail, f)
      else (lines, f)
// files.foreach: (lines, f) =>
//   println(s"file: $f")
//   lines.filter(_.startsWith("permalink: ")).foreach(l => println(s"line:$l"))
files.foreach: (lines, path) =>
  os.write.over(path, lines.mkString("\n"))
println("done")
