package my;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

public class DownloadJSServlet extends HttpServlet {
	private static final long serialVersionUID = 6218135227679830681L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		resp.setContentType("text/JavaScript");
		try {
			Entity e = ds.get(KeyFactory.createKey("JSFile", 1));
			resp.getWriter().println(((Text)e.getProperty("text")).getValue());
		} catch (EntityNotFoundException e) {}
	}
}