set +x

info() {
echo "\033[1;33m[Info]    \033[0m $1"
}
error() {
echo "\033[1;31m[Error]   \033[0m $1"
}
success() {
echo "\033[1;32m[Success] \033[0m $1"
}

RunSpec(){
	info "$1 is preparing to run. Please wait..."
    sleep 5
	mvn clean install compile gauge:execute -DspecsDir=specs/$1
    if [ $? = 0 ]
    	then
            success "The $1 execution has been finished successfully..."
    	else
   			error "Spec execution failed..."
    fi
}

RunSpec Users.spec