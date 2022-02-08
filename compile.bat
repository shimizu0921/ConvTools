javac -sourcepath src -d classes src/tools/ConvTool.java

jar cvfm convtools.jar META-INF/MANIFEST.MF -C classes .