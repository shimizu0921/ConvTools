・ConvTools
	java開発で使用するであろう便利ライブラリ

・使い方
	１、jarファイルのパスを通す
	２、初めにConvTool.init()関数を呼ぶ

・開発者向け
	・コンパイルの仕方(Mac)
		bash compile.bat

	・jarファイルの実行の仕方
		java -jar convtools.jar

	・コンパイルの仕方(batファイルの中身)
		javac -sourcepath src -d classes src/tools/ConvTool.java

	・jarファイルの作成(batファイルの中身)
		jar cvfm convtools.jar META-INF/MANIFEST.MF -C classes .

	・javaファイルの追加
		作成したjavaファイルのパスをマニフェストファイルに追記する
		
		Main-Class: tools.ConvTool
		Class-Path: tools.ConvTool
		Manifest-Version: 1.1.1