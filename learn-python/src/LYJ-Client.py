'''
Created on 2018年8月17日

@author: FengQing
'''
import requests


data = {'tab_id':'1','folderId':'2','sync_ids':'[{\"sync_id\":\"00fs8ynv8\",\"position_in_folder\":1}]'}

r = requests.post('http://127.0.0.1:8000/formtest', data=data)
print(r.text)