package org.pathvisio.pathvisiorpc;

import javax.xml.ws.WebServicePermission;

import org.apache.xmlrpc.*;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcNoSuchHandlerException;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

/**
 * Create a server and a xml server, attribute the handler and start the server.
 * This could be always started using the same port or at least this should 
 * send back the port so it can be used when we call the handlers.
 * @author anwesha, magali
 *
 */
public class RpcServer implements XmlRpcHandlerMapping { 
	
	private int port = getDefaultPort();
	
	private WebServer webserver;
	
	public final int startServer(int port) {
		if(this.port != port) {
			this.port = port;
		}
		  try {
			 System.out.println("Attempting to start Javaserver - XML-RPC Server...");

			 webserver = new WebServer(port);
		     XmlRpcServer xmlserver = webserver.getXmlRpcServer();
		     PropertyHandlerMapping phm = new PropertyHandlerMapping();
		     phm.addHandler("PathwayHandler",PathwayHandler.class);
		     phm.addHandler("MakePgexHandler",MakePgexHandler.class);
			 phm.addHandler("VisXmlHandler", VisualizationXMLHandler.class);
			 phm.addHandler("StatExportHandler", StatExportHandler.class);
			 xmlserver.setHandlerMapping(phm);
			 webserver.start();
			 port = webserver.getPort();
			 System.out.println("Server Started successfully on port "+ port);
			 System.out.println("Accepting requests. (Halt program to stop.)");
		} 
		  catch (Exception exception) {
			 System.err.println("JavaServer: " + exception);
		  }
		return port;
	 }
	
	public final void shutdown() {
		webserver.shutdown();
		System.out.println("Server Stopped successfully ");
	}

	@Override
	public final XmlRpcHandler getHandler(final String arg0)
			throws XmlRpcNoSuchHandlerException, XmlRpcException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public final int getDefaultPort() {
		return 0;
	}
 }