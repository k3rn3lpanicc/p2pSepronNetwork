import socket            
 
# Create a socket object
s = socket.socket()        
 
# Define the port on which you want to connect
port = 8005               
 
# connect to the server on local computer
s.connect(('127.0.0.1', port))

#s.connect(('185.110.189.18', port))

#my_bytes = bytearray()
#my_bytes.append(123)
#my_bytes.append(125)
message = "{'command': 'sendMessage','pPacketCommand':0,'pPacketPayLoad':'salam'}"
print (str(len(message)))
s.send(message.encode())
while(True):
    message = s.recv(1024).decode()
    print(message)
    
