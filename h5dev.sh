#!/usr/bin/env bash
ssh namenode1 "cd red-packets/web-mobile && git pull && source /etc/profile && cnpm run build"
rm -rf /data/www/dapp/red/mobile/dist/
scp -r namenode1:/root/red-packets/web-mobile/dist/ /data/www/dapp/red/mobile/
cp -r /data/www/dapp/red/mobile/dist/static/js /var/www/web/static/
cp -r /data/www/dapp/red/mobile/dist/static/css /var/www/web/static/
cp -r /data/www/dapp/red/mobile/dist/static/fonts /var/www/web/static/
nginx -s reload
nginx -s reload


