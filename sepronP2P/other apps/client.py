import socket            
 
# Create a socket object
s = socket.socket()        
 
# Define the port on which you want to connect
port = 8005               
 
# connect to the server on local computer
s.connect(('127.0.0.1', port))
#my_bytes = bytearray()
#my_bytes.append(123)
#my_bytes.append(125)
message = "hie"
print (str(len(message)))
s.send((str(len(message))+message).encode())
