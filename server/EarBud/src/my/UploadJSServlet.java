package my;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

public class UploadJSServlet extends HttpServlet {
	private static final long serialVersionUID = -2932921921568423249L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Entity e = new Entity(KeyFactory.createKey("JSFile", 1));
		e.setProperty("text", new Text(req.getParameter("code")));
		ds.put(e);
	}
}