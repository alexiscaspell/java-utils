#########################################
# VARIABLES DE USUARIO
#########################################
#ar.com.SomeClass
JAVA_PACKAJE_NAME="$1"
#SomeClass.wsdl
WSDL_FILE_NAME="$2"


#########################################
# VARIABLES DEL BATCH
#########################################
IN_PATH="./in"
OUT_PATH="./out"
CXF_PATH="./cxf"
WSDL_FULL_PATH=$IN_PATH/$WSDL_FILE_NAME


echo -e "RUTAS\n"
echo -e $IN_PATH
echo -e $OUT_PATH
echo -e $CXF_PATH
echo -e $WSDL_FULL_PATH
echo -e "\n"

echo -e "EJECUTANDO...\n"

#########################################
# EJECUCION
#########################################
$CXF_PATH/bin/wsdl2java -p $JAVA_PACKAJE_NAME -d $OUT_PATH -compile -autoNameResolution $WSDL_FULL_PATH 


find . -name "*.class" -type f -delete

echo -e "LISTO\n"