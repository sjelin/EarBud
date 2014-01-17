package my;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

import org.json.JSONArray;
import org.json.JSONException;

public class ConnectServlet extends HttpServlet {
	private static final long serialVersionUID = -786577589627529602L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		String clientID = UUID.randomUUID().toString();
		Key k = KeyFactory.createKey("room", req.getParameter("room"));
		Entity room;
		try {
			room = ds.get(k);
		} catch (EntityNotFoundException e) {
			room = new Entity(k);
		}
		try {
			JSONArray clients = room.hasProperty("clients") ? new JSONArray(((Text)room.getProperty("clients")).getValue()) : new JSONArray();
			clients.put(clientID);
			room.setProperty("clients", new Text(clients.toString()));
		} catch (JSONException e) {}
		ds.put(room);
		resp.setContentType("text/plain");
		resp.getWriter().println(ChannelServiceFactory.getChannelService().createChannel(clientID));
	}
}
