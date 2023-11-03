// useWebSocket.js
import { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

const WebSocket = () => {
  const [data, setData] = useState(null);

  useEffect(() => {
    let socket = new SockJS('http://localhost:8080/websocket-endpoint');
    let stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
      console.log('Connected: ' + frame);
      stompClient.subscribe('/topic/message', function(message) {
        console.log("Received: " + message.body);
        console.log(message);
        setData(message.body);  // Update the state with received data
      });
    });

    // Cleanup on unmount
    return () => {
      if (stompClient) {
        stompClient.disconnect();
      }
    };
  }, []);

  return data;
};

export default WebSocket;

