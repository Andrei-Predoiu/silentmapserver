package calls;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.DataManipulator;
import server.model.Auth;
import server.model.GeneralRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet(urlPatterns = { "/register" })
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new GsonBuilder().create();

	private Configuration cfg = new Configuration();
	DataManipulator worker = new DataManipulator();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterUser() {
		super();
		// TODO Auto-generated constructor stub

		// Specify the data source where the template files come from. Here I
		// set a
		// plain directory for it, but non-file-system are possible too:
		try {
			cfg.setDirectoryForTemplateLoading(new File(
					"D:/Apps/WorkSpace/MSS/WebContent/WEB-INF/templates"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Couldn't load tempaltes dir!!!!!!!");
		}

		// Specify how templates will see the data-model. This is an advanced
		// topic...
		// for now just use this:
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		// Set your preferred charset template files are stored in. UTF-8 is
		// a good choice in most applications:
		cfg.setDefaultEncoding("UTF-8");

		// Sets how errors will appear. Here we assume we are developing HTML
		// pages.
		// For production systems TemplateExceptionHandler.RETHROW_HANDLER is
		// better.
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		// At least in new projects, specify that you want the fixes that
		// aren't
		// 100% backward compatible too (these are very low-risk changes as
		// far
		// as the
		// 1st and 2nd version number remains):
		cfg.setIncompatibleImprovements(new Version(2, 3, 20)); // FreeMarker
		// 2.3.20
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		Map<String, String> root = new HashMap<String, String>();
		root.put("message", "failed");
		try {

			ServletContext sc = this.getServletContext();
			System.out.println(sc.getRealPath(getServletName()));
			String r = request.getParameter("report");
			int i;
			System.out.println(r);
			Auth req = gson.fromJson(r, Auth.class);
			r = gson.toJson(req);
			System.out.println(req.getHash());

			if (!worker.userExsists(req.getHash())) {
				worker.newUser(req.getHash());
				root.put("message", "ok");
			} else {
				root.put("message", "Exists!");

			}

			/* Get the template */
			Template temp = cfg.getTemplate("response.ftl");

			/* Merge data-model with template */
			try {
				temp.process(root, writer);
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			writer.close();
		}

	}
	// TODO Auto-generated method stub
}
