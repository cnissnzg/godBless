import requests
import os
api = 'http://127.0.0.1:12306/api/v1/watermark/judge/getCode'
params = {
    'runId':"e1f4c960-ea14-44ee-8f02-cd9e98eb3861",
    'algorithmId': 1,
    'IP': os.environ['IP'],
}
down_res = requests.get(url=api,params=params)
with open(params['runId']+'.tar.gz',"wb") as code:
    code.write(down_res.content)