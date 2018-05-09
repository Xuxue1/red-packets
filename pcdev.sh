ssh namenode1 "cd red-packets/web-pc && git pull && source /etc/profile && cnpm run build"
ssh namenode1 "rm -rf /data/www/pc/dist/"
ssh namenode1 "cp -r red-packets/web-pc/dist /data/www/pc/"
ssh namenode1 "nginx -s reload"