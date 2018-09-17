'''
Created on 2018年9月11日

@author: FengQing
'''
import time
import redis

# redis连接池
pool = redis.ConnectionPool(host='10.255.33.108', port=6379, decode_responses=True)

# 连接
conn = redis.Redis(connection_pool=pool)
conn2 = redis.Redis(connection_pool=pool)

print(conn.client_list())
print(conn2.client_list())  # 可以看出两个连接的id是一致的，说明是一个客户端连接

#####################
ONE_WEEK_INSECONDS = 7 * 86400
VOTE_SCORE = 432


def article_vote(conn, user, article):
    cutoff = time.time() - ONE_WEEK_INSECONDS
    if conn.zscore('time', article) < cutoff:
        return
    article_id = article.partition(':')[-1]
    if conn.sadd('voted' + article_id, user):
        conn.zincrby('score', article, VOTE_SCORE)
        conn.hincrby(article, 'votes', 1)

