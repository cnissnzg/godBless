import time
from rocketmq.client import PushConsumer, ConsumeStatus
import os

def callback(msg):
    print("?????\n")
    print(msg.id, msg.topic,msg.body)
    return ConsumeStatus.CONSUME_SUCCESS


consumer = PushConsumer(os.environ['IP'])
consumer.set_name_server_address('39.105.21.114:9876')
consumer.subscribe('s2c39d105d21d114',callback)
consumer.subscribe('HTTPServer', callback)
consumer.start()

while True:
    time.sleep(1)

consumer.shutdown()