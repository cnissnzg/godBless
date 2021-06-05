from rocketmq.client import Producer, Message
import sys
import json
import os

producer = Producer('PID-XXX')
producer.set_name_server_address('39.105.21.114:9876')
producer.start()

dat = {
    "runId":sys.argv[1],
    "type":2,
    "detail":{
        "ip":os.environ['IP'],
        "port":"6767"
    }
}

msg = Message('c2s39d105d21d114')
msg.set_keys('XXX')
msg.set_tags('XXX')
msg.set_body(json.dumps(dat))
ret = producer.send_sync(msg)
print(ret.status, ret.msg_id, ret.offset)
producer.shutdown()
