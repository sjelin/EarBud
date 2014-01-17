package my;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

public class SendMsgServlet extends HttpServlet {
	private static final long serialVersionUID = -6617715731338650543L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Key k = KeyFactory.createKey("room", req.getParameter("room"));
		Entity room;
		try {
			room = ds.get(k);
		} catch (EntityNotFoundException e) {
			room = new Entity(k);
		}
		String msg = req.getParameter("msg");
		try {
			JSONArray clients = room.hasProperty("clients") ? new JSONArray(((Text)room.getProperty("clients")).getValue()) : new JSONArray();
			for(int i = 0; i < clients.length(); i++) {
				String client = clients.getString(i);
				ChannelService cs = ChannelServiceFactory.getChannelService();
				cs.sendMessage(new ChannelMessage(client, msg));
			}
		} catch (JSONException e) {}
	}
}
