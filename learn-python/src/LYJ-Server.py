'''
Created on 2018年8月17日

@author: FengQing
'''
import flask 
from flask import request

server = flask.Flask(__name__)

@server.route('/formtest',methods=['post'])
def formtest():
    tab_id = request.values.get('tab_id')
    folderId =request.values.get('folderId')
    sync_ids =request.values.get('sync_ids')
    print(tab_id +' @ '+ folderId +  ' @ ' + sync_ids )
    return

server.run(port=8000, debug=True)