package calls;

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
import server.FreemarkerConfig;
import server.model.GeneralRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet(name = "SaveAreas", urlPatterns = { "/save" })
public class SaveAreas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new GsonBuilder().create();

	private Configuration cfg = FreemarkerConfig.getInstance();
	DataManipulator worker = DataManipulator.getInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveAreas() {
		super();
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
			System.out.println(r);
			GeneralRequest req = gson.fromJson(r, GeneralRequest.class);
			System.out.println(gson.toJson(req));

			if (req.getData().getAction().equals("save")) {
				if (worker.userExsists(req.getAuth().getUsername(), req
						.getAuth().getPassword())) {
					worker.newAreas(req.getAuth().getUsername(), req.getData()
							.getAreas());
					root.put("message", "saved");
				}
			} else if (req.getData().getAction().equals("delete")) {
				if (worker.userExsists(req.getAuth().getUsername(), req
						.getAuth().getPassword())) {

					worker.deleteAreas(req.getAuth().getHash());
					root.put("message", "deleted");
				} else {
					root.put("message", "wrong action");
				}
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
