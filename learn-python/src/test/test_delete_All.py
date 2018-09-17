'''
Created on 2018年9月5日

@author: FengQing
'''
# -*- coding:utf-8 -*-
import requests
import unittest
import json

API_ALL = {
    }
class test_delete_All(unittest.TestCase):

    def setUp(self):
        url = API_ALL['getlist']['url']
        headers = API_ALL['getlist']['headers']
        timeout = API_ALL['getlist']['timeout']
        r = requests.get(url=url, headers=headers, timeout=timeout)
        self.json_data = r.json()
        self.headers = r.headers
        self.status_code = r.status_code
        self.code = r.json()['code']
        res = json.loads(r.text)

        syncid_list = []
        for x in res['data']['note']:
            syncid_list.append((x['sync_id']))
        # print(syncid_list)
        self.syncid_list = syncid_list
        # print(self.syncid_list)
        # print('note_list:'+self.syncid_list)

        folderid_list = []
        for y in res['data']['folder']:
            folderid_list.append(y['sync_id'])
        # print(folderid_list)
        self.folderid_list = folderid_list
        # print('folder_list:'+self.folderid_list)
        pass

    def tearDown(self):
        pass

    def test_01_get_id(self):
        if  'data' in self.json_data:
            self.assertEqual(self.status_code,200)
            self.assertEqual(self.code,0)
            print('get_id_success')
            print('note_list:', self.syncid_list)
            print('folder_list:', self.folderid_list)
            print(self.headers)
            print(self.json_data)
        else:
            print('get_id_fail')


    def test_02_del_note(self):

        url = API_ALL['deletenote']['url']
        headers = API_ALL['deletenote']['headers']
        data = {
                'sync_ids': ",".join(self.syncid_list),
                'tab_id':base_info.tab_id,
            }
        timeout = API_ALL['deletenote']['timeout']
        r = requests.post(url=url,headers=headers,data=data,timeout=timeout)
        # print(r.content)
        status_code = r.status_code
        code = r.json()['code']
        json_data = r.json(