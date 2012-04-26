package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

class ServerThread implements Runnable
{
	Socket incoming;
	PrintWriter out = null;
	BufferedReader in = null;
	String clientMessage = null;
	Server server;
	
	ServerThread(Server server, Socket incoming)
	{
		this.incoming = incoming;
		this.server = server;
	}
	
	public void run()
	{
		try
		{
			incoming.setSoTimeout(5000);
			
			out = new PrintWriter(incoming.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
			
			server.connectClient(this);
			
			while(!server.quit)
			{
				try
				{
					clientMessage = in.readLine();
					
					if (clientMessage != null)
					{
						server.handle(clientMessage);
					}
					else
					{
						server.disconnectClient(this);
						return;
					}
				} catch (SocketException e)
				{
					server.disconnectClient(this);
					return;
				} catch (IOException e)
				{
					server.disconnectClient(this);
					return;
				}
			}
		} catch (IOException e)
		{
			server.disconnectClient(this);
			System.out.println("Terrible error!");
		}
		
	}
	
	public void say(String msg)
	{
		out.println(msg);
	}
	
	public void quit()
	{
		out.close();
		try { in.close(); } catch (IOException e) { }
		try { incoming.close(); } catch (IOException e) { }
	}
}