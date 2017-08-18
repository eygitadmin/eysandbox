package ae.ey.sandbox.experiment;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ae.ey.sandbox.beans.Product;
 
 
@WebServlet(urlPatterns = { "/productList" })
public class ProductListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public ProductListServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        String errorString = null;
        List<Product> productList = new ArrayList<Product>();
        try {
        	
        	DataInputStream dis = new DataInputStream(new FileInputStream(new File("/Users/balarama/Documents/products.txt")));
        	String line = "";
        	Product product;
        	
        	while((line = dis.readLine()) != null) {
        		String[] productValues = line.split(";");
        		//System.out.println(productValues[0] + "---" + productValues[1] + "---" + productValues[2] + "---");
        		product = new Product();
        		product.setName(productValues[0]);
        		product.setCode(productValues[1]);
        		product.setPrice(productValues[2]);
        		
        		productList.add(product);
        	}
        } catch (Exception e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }
   
        // Store info in request attribute, before forward to views
        request.setAttribute("errorString", errorString);
        //System.out.println("Size: " + productList.size());
        for(int ii = 0; ii < productList.size(); ii++) {
        	Product prod = productList.get(ii);
        	System.out.println(prod.getName());
        }
        request.setAttribute("productList", productList);
         
     
        // Forward to /WEB-INF/views/productListView.jsp
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/productListView.jsp");
        dispatcher.forward(request, response);
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
}