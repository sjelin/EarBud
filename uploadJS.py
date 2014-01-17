import requests, os

code = "";

for fil in os.listdir("js"):
    f = open("js/"+fil, "r");
    code += f.read()+"\n";

payload= {'code' : code}

requests.post(r'http://localhost:8888/uploadJS',data=payload)
