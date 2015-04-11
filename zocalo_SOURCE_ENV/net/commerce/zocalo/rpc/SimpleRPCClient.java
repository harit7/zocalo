package net.commerce.zocalo.rpc;

import net.commerce.zocalo.claim.Position;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class SimpleRPCClient {
	private static final Server ZOCALO_SERVER = Server.Zocalo_Local;
	public static final String USERNAME = "Admin";
	private static final double START_BALANCE = 1000;

	private static enum Server { 
		Zocalo_Local("http://localhost:8180/RPC2");
		private String server;
		private XmlRpcClient client;
		private Server(String server){
			this.server = server;
		}
		public XmlRpcClient getRPC() {
			if(client == null){
				XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
                try {
                    config.setServerURL(new URL(server));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                }
                client = new XmlRpcClient();
			    client.setConfig(config);
			}
			return client;
		}
	}
	
	public static String createMarket( String marketName, String outcomes, String description, Integer endowAMM ) {
        XmlRpcClient client = ZOCALO_SERVER.getRPC();

        Vector<Object> params = new Vector<Object>();
        params.addElement(USERNAME);
        params.addElement(marketName);
        params.addElement(outcomes);
        params.addElement(description);
        params.addElement(endowAMM);

        Object result = null;
        try {
            result = client.execute("markets.createMarket", params);
        } catch (XmlRpcException e) {
            e.printStackTrace();
            return null;
        }
        if (result != null && result instanceof String){
			return (String)result;
		}
        return null;
    }

    public static String closeMarket(String marketName, Position winningPos) {
        XmlRpcClient client = ZOCALO_SERVER.getRPC();

        Vector<Object> params = new Vector<Object>();
        params.addElement(USERNAME);
        params.addElement(marketName);
        params.addElement(winningPos.toString());

        Object result = null;
        try {
            result = client.execute("markets.closeMarket", params);
        } catch (XmlRpcException e) {
            e.printStackTrace();
            return null;
        }
        if (result != null && result instanceof String){
			return (String)result;
		}
        return null;
    }
    
	public static String placeMarketTrade(String user, String market, String buySell, Position position, Integer price, Integer shares) {
        XmlRpcClient client = ZOCALO_SERVER.getRPC();

        Vector<Object> params = new Vector<Object>();
        params.addElement(user);
        params.addElement(market);
        params.addElement(buySell);
        params.addElement(position.toString());
        params.addElement(price);
        params.addElement(shares);

        Object result = null;
        try {
            result = client.execute("markets.tradeClaim", params);
        } catch (XmlRpcException e) {
            e.printStackTrace();
            return null;
        }
        if (result != null && result instanceof String){
			return (String)result;
		}
        return null;
	}

	public static Double getCurrentBalance(String userName) {
        XmlRpcClient client = ZOCALO_SERVER.getRPC();

        Vector<Object> params = new Vector<Object>();
        params.addElement(userName);

        Object result = null;
        try {
            result = client.execute("markets.getCurrentBalance", params);
        } catch (XmlRpcException e) {
            e.printStackTrace();
            return null;
        }
        if (result != null && result instanceof Double){
			return (Double)result;
		}
        return null;
	}

	public static Double getMarketMakerPrice(String market, Position position) {
        XmlRpcClient client = ZOCALO_SERVER.getRPC();

        Vector<String> params = new Vector<String>();
        params.addElement(market);
        params.addElement(position.toString());

        Object result = null;
        try {
            result = client.execute("markets.getMarketMakerPrice", params);
        } catch (XmlRpcException e) {
            e.printStackTrace();
            return null;
        }
        if (result != null && result instanceof Double){
			return (Double)result;
		}
        return null;
	}

    public static String grantCash(String userName, double amount) {
        XmlRpcClient client = ZOCALO_SERVER.getRPC();

        Vector<Object> params = new Vector<Object>();
        params.addElement(userName);
        params.addElement(amount);

        Object result = null;
        try {
            result = client.execute("markets.grantCash", params);
        } catch (XmlRpcException e) {
            e.printStackTrace();
            return null;
        }
        if (result != null && result instanceof String){
			return (String)result;
		}
        return null;
    }
    
    public static String transferCash (String fromUserName, String toUserName, double amount) {
    	XmlRpcClient client = ZOCALO_SERVER.getRPC();

        Vector<Object> params = new Vector<Object>();
        params.addElement(fromUserName);
        params.addElement(toUserName);
        params.addElement(amount);

        Object result = null;
        try {
            result = client.execute("markets.transferCash", params);
        } catch (XmlRpcException e) {
            e.printStackTrace();
            return null;            
        }
        if (result != null && result instanceof String) {
			return (String)result;
		}
        return null;
    }
}
