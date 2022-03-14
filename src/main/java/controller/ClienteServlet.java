package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Cliente;
import persistence.ClienteDao;
import persistence.GenericDao;

@WebServlet("/cliente")
public class ClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ClienteServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cliente cli = new Cliente();
		
		String cpf = request.getParameter("cpf");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String limite = request.getParameter("limite");
		String dtNasc = request.getParameter("dtNasc");
		String botao = request.getParameter("botao");
		String op = botao.substring(0, 1);
		
		if (op.equals("A")) {
			op = "U";
		}
		
		GenericDao gDao = new GenericDao();
		ClienteDao cDao = new ClienteDao(gDao);
		String saida = "";
		String erro = "";
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		cli = validaCliente(botao, cpf, nome, email, limite, dtNasc);
		
		try {
			if (botao.equals("Listar")) {
				clientes = cDao.findClientes();
			} else {
				if (cli != null) {
					if (botao.equals("Inserir") || botao.equals("Atualizar") || botao.equals("Deletar")) {
						saida = cDao.iudCliente(op, cli);
						cli = null;
					}
					if (botao.equals("Consultar")) {
						cli = cDao.findCliente(cli);
					} 
				} else {
					erro = "Preencha os campos";
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			request.setAttribute("cliente", cli);
			request.setAttribute("clientes", clientes);
			request.setAttribute("erro", erro);
			request.setAttribute("saida", saida);
			rd.forward(request, response);
		}
		
	}

	private Cliente validaCliente(String botao, String cpf, String nome, String email, String limite, String dtNasc) {
		Cliente cli = new Cliente();
		
		if (botao.equals("Inserir") || botao.equals("Atualizar")) {
			if (cpf.equals("") || nome.equals("") || email.equals("") || limite.equals("") || dtNasc.equals("")) {
				cli = null;
			} else {
				cli.setCpf(cpf);
				cli.setNome(nome);
				cli.setEmail(email);
				cli.setLimite(Float.parseFloat(limite));
				cli.setDtNasc(dtNasc);
			}
		}
		
		if (botao.equals("Consultar") || botao.equals("Deletar")) {
			if (cpf.equals("")) {
				cli = null;
			} else {
				cli.setCpf(cpf);
			}
		}
		
		return cli;
	}

}
