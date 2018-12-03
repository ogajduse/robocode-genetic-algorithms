RT=/usr/lib/jvm/java/jre/lib/rt.jar
SCRIPTPATH="$( cd "$(dirname "$0")" ; pwd -P )"
RUN_CLASS="tanks.RobocodeRunner"
LIBDIR=lib
OUTDIR=out
JAVA=$(type -p java)
JAVAC=$(type -p javac)

CLASSPATH="$RT:$SCRIPTPATH/$OUTDIR/"

for jar in $(ls -1 $SCRIPTPATH/$LIBDIR/)
do
CLASSPATH="$CLASSPATH:$SCRIPTPATH/$LIBDIR/$jar"

done

rm -rf $SCRIPTPATH/$OUTDIR
mkdir $SCRIPTPATH/$OUTDIR

$JAVAC -classpath $CLASSPATH -d $SCRIPTPATH/$OUTDIR $SCRIPTPATH/src/sample/*.java $SCRIPTPATH/src/tanks/*.java

$JAVA -Dfile.encoding=UTF-8 -classpath $CLASSPATH $RUN_CLASS
