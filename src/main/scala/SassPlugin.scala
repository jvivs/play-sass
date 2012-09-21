package net.litola

import sbt._
import sbt.Keys._
import PlayProject._

object SassPlugin extends Plugin {
    val sassEntryPoints = SettingKey[PathFinder]("play-sass-entry-points")
    val sassOptions = SettingKey[Seq[String]]("play-sass-options")
    val sassWatcher = PlayProject.AssetsCompiler("compass",
        { file => (file ** "*.sass") +++ (file ** "*.scss") },
        sassEntryPoints,
        { (name, min) => 
            name.replace(".sass", if (min) ".min.css" else ".css") 
            name.replace(".scss", if (min) ".min.css" else ".css") 
        },
        { SassCompiler.compile _ },
        sassOptions
    )

    override val settings = Seq(
        sassEntryPoints <<= (sourceDirectory in Compile)(base => ((base / "assets" ** "*.sass") +++ (base / "assets" ** "*.scss") --- base / "assets" ** "_*")), 
        sassOptions := Seq.empty[String],
        resourceGenerators in Compile <+= sassWatcher
    )
}


// vim: set ts=4 sw=4 et:
