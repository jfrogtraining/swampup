curl -o jfrog -H "X-JFrog-Art-Api:AKCp2UNgwajwpoz5PghBirpgR5xafggbFyCkSU4DTqK2fQPd9thVcar7wHqk2BNmYjg1km6vJ" http://192.168.1.139:8081/artifactory/jswampup-remote-cache/cli-1.2/mac/jfrog
chmod 755 jfrog
./jfrog mc c --url=http://192.168.1.139:8080 --user=admin --password=password --interactive=false 
./jfrog mc rti attach-lic $1 --bucket-id=1131805150 --bucket-key=6dfbac3d66d839e774809c1f369238cd615123def5b84e5f68617b24002f756e --license-path=$HOME/swampup/artifactory-pro-4.7.6/etc/artifactory.lic

